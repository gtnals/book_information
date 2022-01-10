package com.gtnals.book_information.data;

import java.util.Date;

import lombok.Data;

@Data
public class SuspendHistoryVO {
    private Integer sh_seq;
    private String sh_type;
    private String sh_content;
    private Date sh_reg_dt;
    private Integer sh_si_seq;
}
