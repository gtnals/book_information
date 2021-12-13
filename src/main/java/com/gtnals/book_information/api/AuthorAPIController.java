package com.gtnals.book_information.api;

import com.gtnals.book_information.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorAPIController {
    @Autowired
    AuthorService service;
    // -_-
    
}
