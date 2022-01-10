package com.gtnals.book_information.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gtnals.book_information.data.BookHistoryVO;
import com.gtnals.book_information.data.BookVO;
import com.gtnals.book_information.data.ReservationHistoryVO;
import com.gtnals.book_information.data.ReservationVO;
import com.gtnals.book_information.mapper.BookMapper;
import com.gtnals.book_information.mapper.BorrowMapper;
import com.gtnals.book_information.mapper.ReservationMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired ReservationMapper mapper;
    @Autowired BorrowMapper borrow_mapper;
    @Autowired BookMapper book_mapper;
    public Map<String, Object> getReservationList(Integer offset, String keyword, Integer key_opt, Integer order){
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
        List<ReservationVO> list = mapper.getReservationList(offset, keyword, key_opt, order);
        Integer cnt = mapper.getReservationCnt(keyword, key_opt);
        Integer page_cnt = cnt/10 + (cnt%10>0?1:0);
        resultMap.put("status", true);
        resultMap.put("list", list);
        resultMap.put("total", cnt);
        resultMap.put("pageCnt", page_cnt);
        return resultMap;
    }
    public Map<String, Object> getReservationCntByBook(Integer seq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("cnt", mapper.getReservationCntByBook(seq));
        resultMap.put("status", true);
        return resultMap;
    }
    public Map<String, Object> addReservation(ReservationVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Integer mi_seq = data.getBri_mi_seq();
        Integer bi_seq = data.getBri_bi_seq();
        //해당 회원이 해당 도서를 대출중인 경우
        if(borrow_mapper.isExistBorrow(mi_seq, bi_seq)==1){
            resultMap.put("status", false);
            resultMap.put("message", "본인이 대출 중인 도서는 예약하실 수 없습니다.");
            return resultMap;
        }
        //해당 회원이 해당 도서를 이미 예약한 경우
        if(mapper.isExistReservation(bi_seq, mi_seq)==1){
            resultMap.put("status", false);
            resultMap.put("message", "해당 도서를 이미 예약하셨습니다.");
            return resultMap;
        }
        //해당 회원의 예약 도서가 이미 3권인 경우
        Integer cnt = mapper.getReservationCntByMember(mi_seq);
        if(cnt==3){
            resultMap.put("status", false);
            resultMap.put("message", "예약 가능 권수를 초과하셨습니다. (1인당 3권)");
            return resultMap;
        }
        mapper.addReservation(data);
        resultMap.put("status", true);
        resultMap.put("message", "예약 정보가 추가되었습니다.");

        ReservationHistoryVO history = new ReservationHistoryVO();
        history.setBrh_type("new");
        history.setBrh_content(data.makeHistoryStr());
        Integer recent_seq=mapper.getRecentAddedReservationSeq();
        history.setBrh_bri_seq(recent_seq);
        mapper.insertReservationHistory(history);

        return resultMap;
    }
    public Map<String, Object> deleteReservation(Integer bri_seq, Integer bi_seq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        //해당 도서에 다른 예약 없으면 도서상태 대출가능(0)으로 변경
        if(mapper.getReservationCntByBook(bi_seq)==1){
            BookVO book = book_mapper.getBookInfoBySeq(bi_seq);
            book.setBi_status(0);
            book_mapper.updateBook(book);

            BookHistoryVO history = new BookHistoryVO();
            history.setBh_bi_seq(book.getBi_seq());
            history.setBh_type("update");
            String content = book.getBi_name()+"|"+book.getBi_number()+"|"+book.getBi_ai_seq()+"|"+book.getBi_status()+"|"+
                book.getBi_publisher()+"|"+book.getBi_category()+"|"+book.getBi_publication_date()+"|"+book.getBi_page()+"|"+
                book.getBi_image();
            history.setBh_content(content);
            book_mapper.insertBookHistory(history);
        }
        //다른 예약(대기번호 2) 있으면 1로 변경 및 예약만료일 지정
        else{
            mapper.changePriority(bi_seq);
            mapper.updateDuedate(bi_seq);
        }
        mapper.deleteReservation(bri_seq);
        resultMap.put("status", true);
        resultMap.put("message", "예약 정보를 삭제했습니다.");

        ReservationHistoryVO history = new ReservationHistoryVO();
        history.setBrh_bri_seq(bri_seq);
        history.setBrh_type("delete");
        mapper.insertReservationHistory(history);

        return resultMap;
    }

}
