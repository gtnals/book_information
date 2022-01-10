package com.gtnals.book_information.mapper;

import java.util.List;

import com.gtnals.book_information.data.BoardHistoryVO;
import com.gtnals.book_information.data.BoardVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    public List<BoardVO> getBoardInfo(Integer offset, String keyword, Integer key_opt, Integer order);
    public Integer getBoardCnt(String keyword, Integer key_opt);
    public void deleteBoard(Integer seq);
    public void addReply(BoardVO data);

    public void insertBoardHistory(BoardHistoryVO data);
    public Integer getRecentAddedBoardSeq();
}
