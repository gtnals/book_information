package com.gtnals.book_information.mapper;

import com.gtnals.book_information.data.BorrowVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BorrowMapper {
    public void addBorrow(BorrowVO data);
}
