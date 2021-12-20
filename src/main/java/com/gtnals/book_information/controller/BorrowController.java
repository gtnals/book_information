package com.gtnals.book_information.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BorrowController {
    @GetMapping("/borrow")
    public String getBorrow(Model model){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern("yyyyMMdd");
        String today = formatter.format(c.getTime());
        model.addAttribute("borrow_date", today);  //대출일(오늘)
        c.add(Calendar.DATE, 14);
        String due_date = formatter.format(c.getTime());
        model.addAttribute("due_date", due_date);   //반납일(2주 뒤)

        return "/borrow/list";
    }
}
