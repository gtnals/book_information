package com.gtnals.book_information.data;

import java.text.SimpleDateFormat;
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String start_dt = formatter.format(si_start_dt);
        String end_dt = formatter.format(si_end_dt);
        return si_mi_seq+"|"+start_dt+"|"+end_dt;
    }
}
