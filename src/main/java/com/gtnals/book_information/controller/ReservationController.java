package com.gtnals.book_information.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.gtnals.book_information.service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationController {
    @Autowired ReservationService service;
    @GetMapping("/reservation")
    public String getRservation(
        Model model, @RequestParam @Nullable Integer offset,
        @RequestParam @Nullable String keyword, @RequestParam @Nullable Integer key_opt, 
        @RequestParam @Nullable Integer order 
    ){
        //예약 추가 팝업의 신청일 지정
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern("yyyy-MM-dd (EE)");
        String today = formatter.format(c.getTime());
        model.addAttribute("borrow_date", today);  //신청일(오늘)
        
        model.addAttribute("data", service.getReservationList(offset, keyword, key_opt, order));
        return "/reservation/list";
    }
}
