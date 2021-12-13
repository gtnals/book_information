package com.gtnals.book_information.api;

import java.util.Map;

import com.gtnals.book_information.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorAPIController {
    @Autowired
    AuthorService service;
    @PostMapping("/author/search")
    public Map<String, Object> postBookAdd(@RequestParam String name){
        return service.getAuthorList(name);
    }

}
