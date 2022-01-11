package com.gtnals.book_information.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gtnals.book_information.data.AuthorHistoryVO;
import com.gtnals.book_information.data.AuthorVO;
import com.gtnals.book_information.mapper.AuthorMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorMapper mapper;

    public Map<String, Object> getAuthorList(String name){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        name="%"+name+"%";
        List<AuthorVO> list = mapper.getAuthorListByName(name);
        resultMap.put("status", true);
        resultMap.put("list", list);
        return resultMap;
    }

    public Map<String, Object> addAuthor(AuthorVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        try{
            mapper.addAuthor(data);
        }
        catch(DuplicateKeyException e){
            resultMap.put("status", false);
            resultMap.put("message", "이미 존재하는 저자코드입니다.");
            return resultMap;    
        }
        catch(Exception e){
            resultMap.put("status", false);
            resultMap.put("message", "오류가 발생했습니다.");
            return resultMap; 
        }

        AuthorHistoryVO history = new AuthorHistoryVO();
        history.setAih_type("new");
        history.setAih_content(data.makeHistoryStr());
        Integer recent_seq=mapper.getRecentAddedAuthorSeq();
        history.setAih_ai_seq(recent_seq);

        mapper.insertAuthorHistory(history);

        resultMap.put("status", true);
        resultMap.put("message", "작가를 추가했습니다.");
        return resultMap;
    }

    public Map<String, Object> getAuthor(Integer seq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("status", true);
        resultMap.put("author", mapper.getAuthor(seq));
        return resultMap;
    }
    public Map<String, Object> updateAuthor(AuthorVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try{
            mapper.updateAuthor(data);
        }
        catch(DuplicateKeyException e){
            resultMap.put("status", false);
            resultMap.put("message", "이미 존재하는 저자코드입니다.");
            return resultMap;    
        }
        catch(Exception e){
            resultMap.put("status", false);
            resultMap.put("message", "오류가 발생했습니다.");
            return resultMap; 
        }
        resultMap.put("status", true);
        resultMap.put("message", "작가를 수정했습니다.");

        AuthorHistoryVO history = new AuthorHistoryVO();
        history.setAih_type("modify");
        history.setAih_content(data.makeHistoryStr());
        history.setAih_ai_seq(data.getAi_seq());
        mapper.insertAuthorHistory(history);

        return resultMap;
    }

}
