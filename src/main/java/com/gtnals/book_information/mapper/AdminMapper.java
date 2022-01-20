package com.gtnals.book_information.mapper;

import com.gtnals.book_information.data.AdminVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    public AdminVO loginAdmin(String a_id, String a_pwd);
}
