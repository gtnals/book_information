package com.gtnals.book_information.data;

import java.util.Date;

import lombok.Data;

@Data
public class AuthorVO {
    private Integer ai_seq;
    private String ai_number;
    private String ai_name;
    private Date ai_reg_dt;
    private Date ai_mod_dt;
    private Integer ai_like;
}
