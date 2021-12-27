package com.gtnals.book_information.api;

import java.util.Map;

import com.gtnals.book_information.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardAPIController {
    @Autowired BoardService service;
    @DeleteMapping("/board/delete")
    public Map<String, Object> deleteMember(@RequestParam Integer seq){
        return service.deleteBoard(seq);
    }
}
