package com.gtnals.book_information.mapper;

import java.util.List;

import com.gtnals.book_information.data.AuthorVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorMapper {
    public List<AuthorVO> getAuthorListByName(String name);
}
