package com.gtnals.book_information.data;

import java.util.Date;

import lombok.Data;

@Data
public class SuspendVO {
    private Integer si_seq;
    private Integer si_mi_seq;
    private Date si_start_dt;
    private Date si_end_dt;
    private Date si_reg_dt;
    private Date si_mod_dt;

    private Integer days;

    public String makeHistoryStr(){
        return si_mi_seq+"|"+si_start_dt+"|"+si_end_dt;
    }
}
