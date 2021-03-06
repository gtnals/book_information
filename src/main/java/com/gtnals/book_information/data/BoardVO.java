package com.gtnals.book_information.data;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {
    private Integer mbi_seq;
    private Integer mbi_mi_seq;
    private String mbi_title;
    private String mbi_content;
    private Date mbi_mod_dt;
    private Date mbi_reg_dt;
    private Integer mbi_status;
    private Integer mbi_category;
    private String mbi_reply;

    private String mem_phone;
    private String mem_name;
    private String mem_id;

    public String makeHistoryStr(){
        return mbi_mi_seq+"|"+mbi_title+"|"+mbi_content+"|"+mbi_status+"|"+mbi_category+"|"+mbi_reply;
    }
}
