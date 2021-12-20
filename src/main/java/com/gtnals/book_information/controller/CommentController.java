package com.gtnals.book_information.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommentController {
    @GetMapping("/comment")
    public String getBoard(){
        return "/comment/list";
    }
}
