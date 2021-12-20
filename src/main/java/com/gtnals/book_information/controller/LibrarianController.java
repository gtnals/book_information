package com.gtnals.book_information.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibrarianController {
    @GetMapping("/librarian")
    public String getLibrarian(){
        return "librarian/list";
    }
}
