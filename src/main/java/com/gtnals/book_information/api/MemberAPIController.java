package com.gtnals.book_information.api;

import java.util.Map;

import com.gtnals.book_information.data.MemberVO;
import com.gtnals.book_information.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberAPIController {
    @Autowired
    MemberService service;
    @PostMapping("/member/add")
    public Map<String, Object> postMemberAdd(@RequestBody MemberVO data) throws Exception{
        return service.addMemberInfo(data);
    }
    @DeleteMapping("/member/delete")
    public Map<String, Object> deleteMember(@RequestParam Integer seq){
        return service.deleteMember(seq);
    }
    @GetMapping("/member/get")
    public Map<String, Object> getMember(@RequestParam Integer seq){
        return service.getMember(seq);
    }
    @PatchMapping("/member/update")
    public Map<String, Object> patchMember(@RequestBody MemberVO data){
        return service.updateMember(data);
    }
}
