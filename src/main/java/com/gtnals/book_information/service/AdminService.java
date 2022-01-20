package com.gtnals.book_information.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.gtnals.book_information.data.AdminVO;
import com.gtnals.book_information.mapper.AdminMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired AdminMapper mapper;
    public Map<String, Object> loginAdmin(String a_id, String a_pwd){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        AdminVO admin = mapper.loginAdmin(a_id, a_pwd);
        if(admin==null){
            resultMap.put("status", false);
            resultMap.put("message", "*아이디/비밀번호를 확인해주세요.");
        }
        else {
            resultMap.put("admin", admin);
            resultMap.put("status", true);
        }
        return resultMap;
    }
}
