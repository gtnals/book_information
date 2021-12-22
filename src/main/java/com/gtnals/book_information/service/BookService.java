package com.gtnals.book_information.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gtnals.book_information.data.BookHistoryVO;
import com.gtnals.book_information.data.BookVO;
import com.gtnals.book_information.mapper.BookMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    BookMapper mapper;

    public Map<String, Object> getBookList(Integer offset, String keyword, Integer key_opt, String name,String author,String publisher, Integer category, Integer order){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(offset==null) {
            offset=0;
            resultMap.put("offset", offset);
        }
        if(key_opt == null) {
            key_opt = 0;
        }
        resultMap.put("key_opt", key_opt);
        if(order == null) {
            order = 0;
        }
        resultMap.put("order", order);
// key_opt이 3 => 상세 검색 처리
        if(key_opt == 3){
            resultMap.put("keyword", "");
            if(name==null) {
                name="%%";
                resultMap.put("key_b_name", "");
            }
            else{
                resultMap.put("key_b_name", name);
                name="%"+name+"%";
            }

            if(author==null){
                author="%%";
                resultMap.put("key_b_author", "");
            }
            else{
                resultMap.put("key_b_author", author);
                author="%"+author+"%";
            }

            if(publisher==null){
                publisher="%%";
                resultMap.put("key_b_publisher", "");
            }
            else{
                resultMap.put("key_b_publisher", publisher);
                publisher="%"+publisher+"%";
            }

            if(category == null || category== -1){
                category=-1;
                resultMap.put("key_b_category", category);
            }
            else{
                resultMap.put("key_b_category", category);
            }
            List<BookVO> list = mapper.getBookInfoByDetail(offset, name,author,publisher, category, order);
            Integer cnt = mapper.getBookCountByDetail(name,author,publisher, category);
            resultMap.put("list", list);
            resultMap.put("total", cnt);
            Integer page_cnt = cnt/10 +(cnt%10>0?1:0);
            resultMap.put("pageCnt", page_cnt);
            resultMap.put("status", true);
        }
//key_opt가 0,1,2 -> 일반 검색 처리
        else{
            resultMap.put("key_b_name", "");
            resultMap.put("key_b_author", "");
            resultMap.put("key_b_publisher", "");
            resultMap.put("key_b_category", -1);
            if(keyword==null) {
                keyword="%%";
                resultMap.put("keyword", "");
            }
            else {
                resultMap.put("keyword", keyword);
                keyword = "%"+keyword+"%";
            }
            List<BookVO> list = mapper.getBookInfo(offset,keyword,key_opt, order);
            Integer cnt = mapper.getBookCount(keyword, key_opt);
            resultMap.put("list", list);
            resultMap.put("total", cnt);
            Integer page_cnt = cnt/10 +(cnt%10>0?1:0);
            resultMap.put("status", true);
            resultMap.put("pageCnt", page_cnt);
        }
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
        if(data.getBi_page()==null || data.getBi_page()==0) {
            resultMap.put("status", false);
            resultMap.put("message", "페이지를 입력하세요.");
            return resultMap;
        }
        if(data.getBi_publisher()==null || data.getBi_publisher().equals("")) {
            resultMap.put("status", false);
            resultMap.put("message", "발행처를 입력하세요.");
            return resultMap;
        }
        if(data.getBi_publication_date()==null || data.getBi_publication_date().equals("")) {
            resultMap.put("status", false);
            resultMap.put("message", "발행일을 입력하세요.");
            return resultMap;
        }
        if(data.getBi_category()==null) {
            resultMap.put("status", false);
            resultMap.put("message", "분류를 입력하세요.");
            return resultMap;
        }
        
        try{
            mapper.addBook(data);
        } catch(DuplicateKeyException e){
            resultMap.put("status", false);
            resultMap.put("message", "이미 존재하는 청구번호입니다.");
            return resultMap;    
        } catch(Exception e){
            resultMap.put("status", false);
            resultMap.put("message", "오류가 발생했습니다.");
        }
        resultMap.put("status", true);
        resultMap.put("message", "도서가 추가되었습니다.");

        Integer seq = mapper.selectLatestDataSeq();
        BookHistoryVO history = new BookHistoryVO();
        history.setBh_bi_seq(seq);
        history.setBh_type("new");
        String content = data.getBi_name()+"|"+data.getBi_number()+"|"+data.getBi_ai_seq()+"|"+data.getBi_status()+"|"+
            data.getBi_publisher()+"|"+data.getBi_category()+"|"+data.getBi_publication_date()+"|"+data.getBi_page()+"|"+
            data.getBi_image();
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
        if(data.getBi_publisher()==null || data.getBi_publisher().equals("")) {
            resultMap.put("status", false);
            resultMap.put("message", "발행처를 입력하세요.");
            return resultMap;
        }
        if(data.getBi_category()==null) {
            resultMap.put("status", false);
            resultMap.put("message", "분류를 입력하세요.");
            return resultMap;
        }
        
        try{
            mapper.updateBook(data);
        } catch(DuplicateKeyException e){
            resultMap.put("status", false);
            resultMap.put("message", "이미 존재하는 청구번호입니다.");
            return resultMap;    
        } catch(Exception e){
            resultMap.put("status", false);
            resultMap.put("message", "오류가 발생했습니다.");
        }
        resultMap.put("status", true);
        resultMap.put("message", "수정되었습니다.");

        BookHistoryVO history = new BookHistoryVO();
        history.setBh_bi_seq(data.getBi_seq());
        history.setBh_type("update");
        String content = data.getBi_name()+"|"+data.getBi_number()+"|"+data.getBi_ai_seq()+"|"+data.getBi_status()+"|"+
            data.getBi_publisher()+"|"+data.getBi_category()+"|"+data.getBi_publication_date()+"|"+data.getBi_page()+"|"+
            data.getBi_image();
        history.setBh_content(content);
        mapper.insertBookHistory(history);

        return resultMap;
    }

    public Map<String, Object> getBookbyKeyword(String keyword){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(keyword==null) keyword="%%";
        keyword="%"+keyword+"%";
        List<BookVO> list = mapper.getBookbyKeyword(keyword);
        resultMap.put("status", true);
        resultMap.put("list", list);
        return resultMap;
    }
}
