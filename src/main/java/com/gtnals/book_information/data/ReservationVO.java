package com.gtnals.book_information.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class ReservationVO {
    private Integer bri_seq;
    private Integer bri_bi_seq;
    private Integer bri_mi_seq;
    private Integer bri_priority;
    private Date bri_reg_dt;
    private Date bri_mod_dt;
    private Date bri_due_date;

    private String mem_id;
    private String book_num;

    private String mem_name;
    private String mem_phone;
    private String book_name;

    public String makeHistoryStr(){
        String dt = null;
        if(bri_due_date!=null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dt = formatter.format(bri_due_date);
        }
        return bri_bi_seq+"|"+bri_mi_seq+"|"+bri_priority+"|"+dt;
    }
}
