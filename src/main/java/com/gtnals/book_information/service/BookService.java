package com.gtnals.book_information.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gtnals.book_information.data.BookHistoryVO;
import com.gtnals.book_information.data.BookVO;
import com.gtnals.book_information.mapper.BookMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    BookMapper mapper;

    public Map<String, Object> getBookList(Integer offset, String keyword, Integer key_opt){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(offset==null) {
            offset=0;
            resultMap.put("offset", offset);
        }

        if(keyword==null) {
            keyword="%%";
            resultMap.put("keyword", "");
        }
        else {
            resultMap.put("keyword", keyword);
            keyword = "%"+keyword+"%";
        }

        if(key_opt == null) {
            key_opt = 0;
        }
        resultMap.put("key_opt", key_opt);

        List<BookVO> list = mapper.getBookInfo(offset,keyword,key_opt);

        Integer cnt = mapper.getBookCount(keyword, key_opt);
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
            resultMap.put("message", "청구번호를 입력하세요.");
            return resultMap;
        }
        if(data.getBi_ai_seq()==null || data.getBi_ai_seq()==0) {
            resultMap.put("status", false);
            resultMap.put("message", "저자를 입력하세요.");
            return resultMap;
        }
        
        mapper.addBook(data);
        resultMap.put("status", true);
        resultMap.put("message", "도서가 추가되었습니다.");

        Integer seq = mapper.selectLatestDataSeq();
        BookHistoryVO history = new BookHistoryVO();
        history.setBh_bi_seq(seq);
        history.setBh_type("new");
        String content = data.getBi_name()+"|"+data.getBi_number()+"|"+data.getBi_ai_seq()+"|"+data.getBi_status();
        history.setBh_content(content);
        mapper.insertBookHistory(history);

        return resultMap;
    }

    public Map<String, Object> deleteBook(Integer seq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        mapper.deleteBook(seq);
        resultMap.put("status", true);
        resultMap.put("message", "도서가 삭제되었습니다.");

        BookHistoryVO history = new BookHistoryVO();
        history.setBh_bi_seq(seq);
        history.setBh_type("delete");
        mapper.insertBookHistory(history);

        return resultMap;
    }

    public Map<String, Object> getBookInfoBySeq(Integer seq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("status", true);
        resultMap.put("data", mapper.getBookInfoBySeq(seq));
        return resultMap;
    }

    public Map<String, Object> updateBookInfo(BookVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        mapper.updateBook(data);

        resultMap.put("status", true);
        resultMap.put("message", "수정되었습니다.");

        BookHistoryVO history = new BookHistoryVO();
        history.setBh_bi_seq(data.getBi_seq());
        history.setBh_type("update");
        String content = data.getBi_name()+"|"+data.getBi_number()+"|"+data.getBi_ai_seq()+"|"+data.getBi_status();
        history.setBh_content(content);
        mapper.insertBookHistory(history);

        return resultMap;
    }
}
