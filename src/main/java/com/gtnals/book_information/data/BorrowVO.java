package com.gtnals.book_information.data;

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
}
