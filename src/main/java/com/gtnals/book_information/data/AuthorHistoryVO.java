package com.gtnals.book_information.data;

import lombok.Data;

@Data
public class AuthorHistoryVO {
    private Integer aih_seq;
    private Integer aih_ai_seq;
    private String aih_type;
    private String aih_content;
    private Integer aih_reg_dt;
}
