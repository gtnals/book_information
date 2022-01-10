package com.gtnals.book_information.api;

import java.util.Map;

import com.gtnals.book_information.data.ReservationVO;
import com.gtnals.book_information.service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationAPIController {
    @Autowired ReservationService service;
    @GetMapping("/reservation/search")
    public Map<String, Object> getReservationCntByBook(@RequestParam Integer seq){
        return service.getReservationCntByBook(seq);
    }
    @PostMapping("/reservation/add")
    public Map<String, Object> postReservationAdd(@RequestBody ReservationVO data){
        return service.addReservation(data);
    }
    @DeleteMapping("/reservation/delete")
    public Map<String, Object> deleteReservation(@RequestParam Integer bri_seq, @RequestParam Integer bi_seq){
        return service.deleteReservation(bri_seq, bi_seq);
    }
}
