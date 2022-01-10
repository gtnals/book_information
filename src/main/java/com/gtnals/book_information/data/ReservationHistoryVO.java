package com.gtnals.book_information.data;

import java.util.Date;

import lombok.Data;

@Data
public class ReservationHistoryVO {
    private Integer brh_seq;
    private String brh_type;
    private String brh_content;
    private Date brh_reg_dt;
    private Integer brh_bri_seq;
}
