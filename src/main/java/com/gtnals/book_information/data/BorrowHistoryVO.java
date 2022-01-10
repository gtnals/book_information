package com.gtnals.book_information.data;

import java.util.Date;

import lombok.Data;

@Data
public class BorrowHistoryVO {
    private Integer bbh_seq;
    private String bbh_type;
    private String bbh_content;
    private Date bbh_reg_dt;
    private Integer bbh_bbi_seq;
}
