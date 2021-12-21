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
    private String ai_email;
    private String ai_phone;
    private String ai_insta;

    public String makeHistoryStr(){
        return ai_number+"|"+ai_name+"|"+ai_email+"|"+ai_phone+"|"+ai_insta;
    }
}
