package com.gtnals.book_information.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    @GetMapping("/member")
    public String getMember(){
        return "/member/list";
    }
}
