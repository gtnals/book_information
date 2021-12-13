package com.gtnals.book_information.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gtnals.book_information.data.BookVO;
import com.gtnals.book_information.mapper.BookMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    BookMapper mapper;

    public Map<String, Object> getBookList(Integer offset){
        if(offset==null) offset=0;

        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        List<BookVO> list = mapper.getBookInfo(offset);

        Integer cnt = mapper.getBookCount();
        Integer page_cnt = cnt/10 +(cnt%10>0?1:0);

        resultMap.put("status", true);
        resultMap.put("total", cnt);
        resultMap.put("pageCnt", page_cnt);
        resultMap.put("list", list);
        return resultMap;
    }

    public Map<String, Object> addBook(BookVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(data.getBi_name()==null || data.getBi_name().equals("")) {
            resultMap.put("status", false);
            resultMap.put("message", "도서명을 입력하세요.");
            return resultMap;
        }
        if(data.getBi_number()==null || data.getBi_number().equals("")) {
            resultMap.put("status", false);
            resultMap.put("message", "일련번호를 입력하세요.");
            return resultMap;
        }
        
        mapper.addBook(data);
        resultMap.put("status", true);
        resultMap.put("message", "도서가 추가되었습니다.");
        return resultMap;
    }

    public Map<String, Object> deleteBook(Integer seq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        mapper.deleteBook(seq);
        resultMap.put("status", true);
        resultMap.put("message", "도서가 삭제되었습니다.");
        return resultMap;
    }
}
