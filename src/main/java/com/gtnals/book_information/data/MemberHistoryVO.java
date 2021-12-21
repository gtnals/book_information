package com.gtnals.book_information.data;

import lombok.Data;

@Data
public class MemberHistoryVO {
    private Integer mih_seq;
    private Integer mih_mi_seq;
    private String mih_type;
    private String mih_mi_content;
    private Integer mih_reg_dt;
}
