package com.gtnals.book_information.mapper;

import java.util.List;

import com.gtnals.book_information.data.BorrowVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BorrowMapper {
    public void addBorrow(BorrowVO data);
    public List<BorrowVO> getBorrowList(Integer offset, String keyword, Integer key_opt, Integer order);
    public Integer getBorrowCnt(String keyword, Integer key_opt);
}