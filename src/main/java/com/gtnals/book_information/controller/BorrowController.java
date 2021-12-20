package com.gtnals.book_information.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.gtnals.book_information.service.BorrowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BorrowController {
    @Autowired
    BorrowService service;
    @GetMapping("/borrow")
    public String getBorrow(
        Model model, @RequestParam @Nullable Integer offset,
        @RequestParam @Nullable String keyword, @RequestParam @Nullable Integer key_opt, 
        @RequestParam @Nullable Integer order
    ){
        //대출 추가 팝업의 대출일 및 반납일 지정
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern("yyyy-MM-dd");
        String today = formatter.format(c.getTime());
        model.addAttribute("borrow_date", today);  //대출일(오늘)
        
        c.add(Calendar.DATE, 14);
        String due_date = formatter.format(c.getTime());
        model.addAttribute("due_date", due_date);   //반납일(2주 뒤)

        model.addAttribute("data", service.getBorrowList(offset, keyword, key_opt, order));
        return "/borrow/list";
    }
}
