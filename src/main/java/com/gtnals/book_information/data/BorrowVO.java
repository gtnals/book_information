package com.gtnals.book_information.data;

import java.util.Date;

import lombok.Data;

@Data
public class BorrowVO {
    private Integer bbi_seq;
    private Integer bbi_bi_seq;
    private Integer bbi_mi_seq;
    private String bbi_borrow_date;
    private String bbi_due_date;
    private Date bbi_reg_dt;
    private Date bbi_mod_dt;

    private String bbi_mem_id;
    private String bbi_book_num;
}
