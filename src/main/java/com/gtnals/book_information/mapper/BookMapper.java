package com.gtnals.book_information.mapper;

import java.util.List;

import com.gtnals.book_information.data.BookHistoryVO;
import com.gtnals.book_information.data.BookVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper {
    public List<BookVO>  getBookInfo(Integer offset, String keyword, Integer key_opt, Integer order);
    public Integer getBookCount(String keyword, Integer key_opt);
    public void addBook(BookVO data);
    public void deleteBook(Integer seq);
    public BookVO getBookInfoBySeq(Integer seq);
    public void updateBook(BookVO data);
    public Integer selectLatestDataSeq();
    public void insertBookHistory(BookHistoryVO data);
    public List<BookVO> getBookInfoByDetail(Integer offset, String name,String author,String publisher, Integer category, Integer order);
    public Integer getBookCountByDetail(String name,String author,String publisher, Integer category);

    public BookVO getBookInfoByNum(String bi_number);

    public List<BookVO> getBookbyKeyword(String keyword);
}
