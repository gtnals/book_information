package com.gtnals.book_information.api;

import java.util.Map;

import com.gtnals.book_information.data.AuthorVO;
import com.gtnals.book_information.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorAPIController {
    @Autowired
    AuthorService service;
    @PostMapping("/author/search")
    public Map<String, Object> postAuthorSearch(@RequestParam String name){
        return service.getAuthorList(name);
    }
    @PostMapping("/author/add")
    public Map<String, Object> postAuthorAdd(@RequestBody AuthorVO data){
        return service.addAuthor(data);
    }
    @GetMapping("/author/get")
    public Map<String, Object> getAuthor(@RequestParam Integer seq){
        return service.getAuthor(seq);
    }
    @PatchMapping("/author/update")
    public Map<String, Object> patchAuthor(@RequestBody AuthorVO data){
        return service.updateAuthor(data);
    }
}
