package com.gtnals.book_information.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gtnals.book_information.data.BookVO;
import com.gtnals.book_information.data.BorrowVO;
import com.gtnals.book_information.mapper.BookMapper;
import com.gtnals.book_information.mapper.BorrowMapper;
import com.gtnals.book_information.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowService {
    @Autowired BorrowMapper mapper;
    @Autowired MemberMapper m_mapper;
    @Autowired BookMapper b_mapper;
    public Map<String, Object> addBorrow(BorrowVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Integer bbi_bi_seq = 0;
        BookVO book = b_mapper.getBookInfoByNum(data.getBook_num());
        try{
            bbi_bi_seq = book.getBi_seq();
        }
        catch(NullPointerException e){
            resultMap.put("status", false);
            resultMap.put("message", "존재하지 않는 도서 청구번호입니다.");
            return resultMap;
        }

        Integer bbi_mi_seq=0;
        try{
            bbi_mi_seq = m_mapper.getMemberByID(data.getMem_id()).getMi_seq();
        }
        catch(NullPointerException e){
            resultMap.put("status", false);
            resultMap.put("message", "존재하지 않는 회원ID입니다.");
            return resultMap;
        }
        book.setBi_status(1);
        b_mapper.updateBook(book); //도서 상태를 대출중으로 변경
        data.setBbi_bi_seq(bbi_bi_seq);
        data.setBbi_mi_seq(bbi_mi_seq);
        mapper.addBorrow(data);
        resultMap.put("status", true);
        resultMap.put("message", "대출 정보를 등록했습니다.");
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
        resultMap.put("list", list);
        resultMap.put("total", cnt);
        resultMap.put("pageCnt", page_cnt);
        resultMap.put("status", true);
        return resultMap;
    }
}
