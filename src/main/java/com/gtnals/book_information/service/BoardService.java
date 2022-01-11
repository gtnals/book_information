package com.gtnals.book_information.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gtnals.book_information.data.BoardHistoryVO;
import com.gtnals.book_information.data.BoardVO;
import com.gtnals.book_information.mapper.BoardMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired BoardMapper mapper;
    public Map<String, Object> getBoardList(Integer offset, String keyword, Integer key_opt, Integer order){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(offset==null){
            offset=0;
            resultMap.put("offset", offset);
        }
        if(key_opt==null) key_opt=0;
        resultMap.put("key_opt", key_opt);
        if(order==null) order=0;
        resultMap.put("order", order);
        if(keyword==null){
            keyword="%%";
            resultMap.put("keyword","");
        }
        else{
            resultMap.put("keyword", keyword);
            keyword="%"+keyword+"%";
        }
        List<BoardVO> list = mapper.getBoardInfo(offset, keyword, key_opt, order);
        Integer cnt = mapper.getBoardCnt(keyword, key_opt);
        Integer page_cnt = cnt/10 + (cnt%10>0?1:0);
        resultMap.put("status", true);
        resultMap.put("list", list);
        resultMap.put("total", cnt);
        resultMap.put("pageCnt", page_cnt);
        return resultMap;
    }

    public Map<String, Object> deleteBoard(Integer seq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        mapper.deleteBoard(seq);
        resultMap.put("status", true);
        resultMap.put("message", "게시글이 삭제되었습니다.");

        BoardHistoryVO history = new BoardHistoryVO();
        history.setMbh_mbi_seq(seq);
        history.setMbh_type("delete");
        mapper.insertBoardHistory(history);

        return resultMap;
    }

    public Map<String, Object> addReply(BoardVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        mapper.addReply(data);
        resultMap.put("status", true);
        resultMap.put("message", "답변이 등록되었습니다.");

        BoardHistoryVO history = new BoardHistoryVO();
        history.setMbh_type("modify");
        BoardVO board = mapper.getBoard(data.getMbi_seq());
        history.setMbh_content(board.makeHistoryStr());
        history.setMbh_mbi_seq(board.getMbi_seq());
        mapper.insertBoardHistory(history);

        return resultMap;
    }
}
