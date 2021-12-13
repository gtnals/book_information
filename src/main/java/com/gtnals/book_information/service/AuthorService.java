package com.gtnals.book_information.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gtnals.book_information.data.AuthorVO;
import com.gtnals.book_information.mapper.AuthorMapper;

import org.springframework.beans.factory.annotation.Autowired;

public class AuthorService {
    @Autowired
    AuthorMapper mapper;

    public Map<String, Object> getAuthorList(String name){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        List<AuthorVO> list = mapper.getAuthorListByName(name);
        resultMap.put("status", true);
        resultMap.put("list", list);
        return resultMap;
    }
}
