package com.gtnals.book_information.controller;

import com.gtnals.book_information.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
    @Autowired
    MemberService service;
    @GetMapping("/member")
    public String getMember(
        Model model, @RequestParam @Nullable Integer offset,
        @RequestParam @Nullable String keyword, @RequestParam @Nullable Integer key_opt, 
        @RequestParam @Nullable Integer order
    ){
        model.addAttribute("data", service.getMemberList(offset, keyword, key_opt, order));
        return "/member/list";
    }
}
