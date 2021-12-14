package com.gtnals.book_information.data;

import java.util.Date;

import lombok.Data;

@Data
public class BookHistoryVO {
    private Integer bh_seq;
    private String bh_type;
    private String bh_content;
    private Date bh_reg_dt;
    private Integer bh_bi_seq;
}
