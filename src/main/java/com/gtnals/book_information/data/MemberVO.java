package com.gtnals.book_information.data;

import java.util.Date;

import lombok.Data;

@Data
public class MemberVO {
    private Integer mi_seq;
    private String mi_id;
    private String mi_pwd;
    private String mi_name;
    private String mi_birth;  
    private String mi_phone;  
    private String mi_email;
    private Integer mi_status;
    private Date mi_mod_dt;
    private Date mi_reg_dt;

    public String makeHistoryStr(){
        return mi_id+"|"+mi_name+"|"+mi_birth+"|"+mi_phone+"|"+mi_email+"|"+mi_status;
    }
}
