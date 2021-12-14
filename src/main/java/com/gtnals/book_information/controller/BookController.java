package com.gtnals.book_information.controller;

import java.util.Map;

import com.gtnals.book_information.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {
    @Autowired
    BookService service;
    @GetMapping("/book")
    public String getBook(
        Model model, @RequestParam @Nullable Integer offset,
        @RequestParam @Nullable String keyword
    ){
        Map<String, Object> resultMap=service.getBookList(offset,keyword);
        model.addAttribute("data", resultMap);
        return "/book/list";
    }
}
