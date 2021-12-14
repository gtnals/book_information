package com.gtnals.book_information.data;

import java.util.Date;

import lombok.Data;

@Data
public class BookVO {
    private Integer bi_seq;
    private Integer bi_ai_seq;
    private Integer bi_ci_seq;
    private String bi_name;
    private String bi_number;
    private Date bi_reg_dt;
    private Date bi_mod_dt;
    private Integer bi_status;
    private Integer bi_like;
    private Integer bi_cnt;
    private String bi_sub;
    private String bi_publication_date;
    private String bi_publisher;
    private String bi_translator;
    private String bi_image;
    private Integer bi_page;

    private String author;
    private String author_code;
}
