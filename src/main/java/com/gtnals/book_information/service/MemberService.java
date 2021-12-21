package com.gtnals.book_information.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gtnals.book_information.data.MemberHistoryVO;
import com.gtnals.book_information.data.MemberVO;
import com.gtnals.book_information.mapper.MemberMapper;
import com.gtnals.book_information.utils.AESAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    MemberMapper mapper;

    public Map<String, Object> getMemberList(Integer offset, String keyword, Integer key_opt, Integer order){
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
        List<MemberVO> list = mapper.getMemberInfo(offset, keyword, key_opt, order);
        Integer cnt = mapper.getMemberCnt(keyword, key_opt);
        Integer page_cnt = cnt/10 + (cnt%10>0?1:0);
        resultMap.put("list", list);
        resultMap.put("total", cnt);
        resultMap.put("pageCnt", page_cnt);
        resultMap.put("status", true);
        return resultMap;
    }
    
    public Map<String, Object> addMemberInfo(MemberVO data) throws Exception{
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(data.getMi_birth().equals("") || data.getMi_birth()==null){
            resultMap.put("status", false);
            resultMap.put("reason", "birth");
            resultMap.put("message", "생년월일을 입력해주세요");
            return resultMap;
        }
        if(data.getMi_email().equals("") || data.getMi_email()==null){
            resultMap.put("status", false);
            resultMap.put("reason", "email");
            resultMap.put("message", "이메일을 입력해주세요");
            return resultMap;
        }
        if(data.getMi_phone().equals("") || data.getMi_phone()==null){
            resultMap.put("status", false);
            resultMap.put("reason", "phone");
            resultMap.put("message", "전화번호를 입력해주세요");
            return resultMap;
        }
        String pwd=data.getMi_pwd();
        String encrypted = AESAlgorithm.Encrypt(pwd);
        data.setMi_pwd(encrypted);
        System.err.println(encrypted);
        try{
            mapper.addMemberInfo(data);
        }
        catch(DuplicateKeyException e){
            resultMap.put("status", false);
            resultMap.put("message", "이미 존재하는 아이디입니다.");
            return resultMap;    
        } catch(Exception e){
            resultMap.put("status", false);
            resultMap.put("message", "오류가 발생했습니다.");
            return resultMap; 
        }

        MemberHistoryVO history = new MemberHistoryVO();
        history.setMih_type("new");
        history.setMih_mi_content(data.makeHistoryStr());
        Integer recent_seq=mapper.getRecentAddedMemberSeq();
        history.setMih_mi_seq(recent_seq);
        mapper.insertMemberHistory(history);

        resultMap.put("status", true);
        resultMap.put("message", "회원 정보가 추가되었습니다.");
        return resultMap;
    }

    public Map<String, Object> deleteMember(Integer seq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        mapper.deleteMember(seq);
        resultMap.put("status", true);
        resultMap.put("message", "회원이 삭제되었습니다.");

        MemberHistoryVO history = new MemberHistoryVO();
        history.setMih_type("delete");
        history.setMih_mi_seq(seq);
        mapper.insertMemberHistory(history);

        return resultMap;
    }

    public Map<String, Object> getMember(Integer seq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("status", true);
        resultMap.put("data", mapper.getMember(seq));
        return resultMap;
    }
    
    public Map<String, Object> updateMember(MemberVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(data.getMi_birth().equals("") || data.getMi_birth()==null){
            resultMap.put("status", false);
            resultMap.put("reason", "birth");
            resultMap.put("message", "생년월일을 입력해주세요");
            return resultMap;
        }
        if(data.getMi_email().equals("") || data.getMi_email()==null){
            resultMap.put("status", false);
            resultMap.put("reason", "email");
            resultMap.put("message", "이메일을 입력해주세요");
            return resultMap;
        }
        if(data.getMi_phone().equals("") || data.getMi_phone()==null){
            resultMap.put("status", false);
            resultMap.put("reason", "phone");
            resultMap.put("message", "전화번호를 입력해주세요");
            return resultMap;
        }
        try{
            mapper.updateMember(data);
        }
        catch(DuplicateKeyException e){
            resultMap.put("status", false);
            resultMap.put("message", "이미 존재하는 아이디입니다.");
            return resultMap;    
        } catch(Exception e){
            resultMap.put("status", false);
            resultMap.put("message", "오류가 발생했습니다.");
        }
        resultMap.put("status", true);
        resultMap.put("message", "수정되었습니다.");

        MemberHistoryVO history = new MemberHistoryVO();
        history.setMih_type("modify");
        history.setMih_mi_content(data.makeHistoryStr());
        history.setMih_mi_seq(data.getMi_seq());
        mapper.insertMemberHistory(history);

        return resultMap;
    }
}
