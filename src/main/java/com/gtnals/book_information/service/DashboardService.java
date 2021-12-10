package com.gtnals.book_information.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gtnals.book_information.mapper.DashboardMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired DashboardMapper mapper;

    public Map<String, Object> getCounts(){
        List<Integer> bookCntList = new ArrayList<Integer>();
        bookCntList.add(mapper.getTotalBookCnt());
        bookCntList.add(mapper.getAvailableBookCnt());
        bookCntList.add(mapper.getBorrowBookCnt());
        bookCntList.add(mapper.getOverdueBookCnt());
        bookCntList.add(mapper.getReserveBookCnt());

        List<Integer> memberCntList = new ArrayList<Integer>();
        memberCntList.add(mapper.getTotalMemberCnt());
        memberCntList.add(mapper.getNormalMemberCnt());
        memberCntList.add(mapper.getWarningMemberCnt());
        memberCntList.add(mapper.getDeactiveMemberCnt());

        List<Integer> reviewCntList = new ArrayList<Integer>();
        reviewCntList.add(mapper.getTotalReviewCnt());
        reviewCntList.add(mapper.getPrivateReviewCnt());
        reviewCntList.add(mapper.getNormalReviewCnt());
        reviewCntList.add(mapper.getwaitDeleteCnt());

        List<Integer> boardCntList = new ArrayList<Integer>();
        boardCntList.add(mapper.getTotalBoardCnt());
        boardCntList.add(mapper.getAddBookBoardCnt());
        boardCntList.add(mapper.getReportBookBoardCnt());
        boardCntList.add(mapper.getEtcBoardCnt());
        boardCntList.add(mapper.getCompleteBoardCnt());
        boardCntList.add(mapper.getWaitBoardCnt());

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("book", bookCntList);
        map.put("member", memberCntList);
        map.put("review", reviewCntList);
        map.put("board", boardCntList);
        return map;
    }
}
