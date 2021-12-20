package com.gtnals.book_information.api;

import java.util.Map;

import com.gtnals.book_information.data.BorrowVO;
import com.gtnals.book_information.service.BorrowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowAPIController {
    @Autowired BorrowService service;
    @PostMapping("/borrow/add")
    public Map<String, Object>  postBorrowAdd(@RequestBody BorrowVO data){
        return service.addBorrow(data);
    }
}
