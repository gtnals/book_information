package com.gtnals.book_information.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    @GetMapping("/board")
    public String getBoard(){
        return "/board/list";
    }
}
