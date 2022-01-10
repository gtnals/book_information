package com.gtnals.book_information.data;

import java.util.Date;

import lombok.Data;

@Data
public class BoardHistoryVO {
    private Integer mbh_seq;
    private String mbh_type;
    private String mbh_content;
    private Date mbh_reg_dt;
    private Integer mbh_mbi_seq;
}
