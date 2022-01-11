package com.gtnals.book_information.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class BorrowVO {
    private Integer bbi_seq;
    private Integer bbi_bi_seq;
    private Integer bbi_mi_seq;
    private Date bbi_borrow_date;
    private Date bbi_due_date;
    private Date bbi_reg_dt;
    private Date bbi_mod_dt;

    private String mem_id;
    private String book_num;

    private String mem_name;
    private String mem_phone;
    private String book_name;

    public String makeHistoryStr(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String borrow_dt = formatter.format(bbi_borrow_date);
        String due_dt = formatter.format(bbi_due_date);
        return bbi_bi_seq+"|"+bbi_mi_seq+"|"+borrow_dt+"|"+due_dt;
    }
}
