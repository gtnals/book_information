package com.gtnals.book_information.data;

import java.util.Date;

import lombok.Data;

@Data
public class AdminVO {
    private Integer a_seq;
    private String a_id;
    private String a_pwd;
    private String a_name;
    private String a_birth;
    private String a_phone;
    private Date a_reg_dt;
    private Date a_mod_dt;
}
