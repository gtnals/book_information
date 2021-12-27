package com.gtnals.book_information.controller;

import com.gtnals.book_information.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {
    @Autowired BoardService service;
    @GetMapping("/board")
    public String getBoard(Model model, @RequestParam @Nullable Integer offset,
    @RequestParam @Nullable String keyword, @RequestParam @Nullable Integer key_opt, 
    @RequestParam @Nullable Integer order
){
    model.addAttribute("data", service.getBoardList(offset, keyword, key_opt, order));
    return "/board/list";
}
}
