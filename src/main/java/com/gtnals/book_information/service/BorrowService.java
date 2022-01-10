package com.gtnals.book_information.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gtnals.book_information.data.BookVO;
import com.gtnals.book_information.data.BorrowHistoryVO;
import com.gtnals.book_information.data.BorrowVO;
import com.gtnals.book_information.data.MemberVO;
import com.gtnals.book_information.data.SuspendHistoryVO;
import com.gtnals.book_information.data.SuspendVO;
import com.gtnals.book_information.mapper.BookMapper;
import com.gtnals.book_information.mapper.BorrowMapper;
import com.gtnals.book_information.mapper.MemberMapper;
import com.gtnals.book_information.mapper.ReservationMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowService {
    @Autowired BorrowMapper mapper;
    @Autowired MemberMapper m_mapper;
    @Autowired BookMapper b_mapper;
    @Autowired ReservationMapper r_mapper;
    public Map<String, Object> addBorrow(BorrowVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        BookVO book = b_mapper.getBookInfoBySeq(data.getBbi_bi_seq());
        MemberVO mem = m_mapper.getMember(data.getBbi_mi_seq());

    //인당 5권 제한=>
        if(mapper.getMemBorrowCnt(mem.getMi_seq())==5){
            resultMap.put("status", false);
            resultMap.put("message", "최대 대출 권수를 초과하셨습니다. (1인당 5권)");
            return resultMap;
        }
    //대출 진행 =>
        book.setBi_status(1);
        book.setBi_cnt(book.getBi_cnt()+1);
        b_mapper.updateBook(book); //도서 상태를 대출중으로 변경, 대출수+1
        
        mapper.addBorrow(data);
        resultMap.put("status", true);
        resultMap.put("message", "대출 정보를 등록했습니다.");

        BorrowHistoryVO history = new BorrowHistoryVO();
        history.setBbh_type("new");
        history.setBbh_content(data.makeHistoryStr());
        Integer recent_seq=mapper.getRecentAddedBorrowSeq();
        history.setBbh_bbi_seq(recent_seq);

        mapper.insertBorrowHistory(history);

        return resultMap;
    }

    public Map<String, Object> getBorrowList(Integer offset, String keyword, Integer key_opt, Integer order){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(offset==null){
            offset=0;
            resultMap.put("offset", offset);
        }
        if(key_opt==null) key_opt=0;
        resultMap.put("key_opt", key_opt);
        if(order==null) order=0;
        resultMap.put("order", order);
        if(keyword==null){
            keyword="%%";
            resultMap.put("keyword","");
        }
        else{
            resultMap.put("keyword", keyword);
            keyword="%"+keyword+"%";
        }
        List<BorrowVO> list = mapper.getBorrowList(offset, keyword, key_opt, order);
        Integer cnt = mapper.getBorrowCnt(keyword, key_opt);
        Integer page_cnt = cnt/10 + (cnt%10>0?1:0);
        resultMap.put("status", true);
        resultMap.put("list", list);
        resultMap.put("total", cnt);
        resultMap.put("pageCnt", page_cnt);
        return resultMap;
    }

    public Map<String, Object> deleteBorrow(Integer seq, Integer bi_seq, Integer mi_seq, Integer days){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        BookVO book= b_mapper.getBookInfoBySeq(bi_seq);
        Integer this_book = book.getBi_status();    //연체 도서인지 확인 및 상태 저장

        //예약 여부 따라 도서 상태 변경
        if(r_mapper.getReservationCntByBook(bi_seq)==0){    //예약 안 걸린 경우, 대출가능(0) 설정
            book.setBi_status(0);
        }
        else {   //예약 걸려 있는 경우, 3(예약 중) 설정하고, 해당 예약 정보에 예약 만료일 지정
            book.setBi_status(3);
            r_mapper.updateDuedate(bi_seq);
        }
        b_mapper.updateBook(book);

        //seq로 대출 정보 삭제
        mapper.deleteBorrowInfo(seq);
        resultMap.put("status", true);

        //정지 회원이고, 연체 도서였던 경우 정지 정보 추가
        if(m_mapper.getMember(mi_seq).getMi_status()==2&&this_book==2){
            m_mapper.addSuspendInfo(mi_seq, days*2);    //연체일수*2일 간 정지
            resultMap.put("message", "반납 및 회원정지 처리되었습니다.");

            SuspendHistoryVO history = new SuspendHistoryVO();
            history.setSh_type("new");
            Integer recent_seq=m_mapper.getRecentAddedSuspendSeq();
            history.setSh_si_seq(recent_seq);
            SuspendVO data = m_mapper.getSuspendInfo(recent_seq);
            history.setSh_content(data.makeHistoryStr());
            m_mapper.insertSuspendHistory(history);

            return resultMap;
        }
        resultMap.put("message", "반납 처리되었습니다.");

        BorrowHistoryVO history = new BorrowHistoryVO();
        history.setBbh_bbi_seq(seq);
        history.setBbh_type("delete");
        mapper.insertBorrowHistory(history);

        return resultMap;
    }
}
