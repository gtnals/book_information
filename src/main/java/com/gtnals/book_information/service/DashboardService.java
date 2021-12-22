package com.gtnals.book_information.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gtnals.book_information.data.BookVO;
import com.gtnals.book_information.data.BorrowVO;
import com.gtnals.book_information.data.MemberVO;
import com.gtnals.book_information.mapper.BookMapper;
import com.gtnals.book_information.mapper.BorrowMapper;
import com.gtnals.book_information.mapper.DashboardMapper;
import com.gtnals.book_information.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired DashboardMapper mapper;
    @Autowired BookMapper book_mapper;
    @Autowired BorrowMapper borrow_mapper;
    @Autowired MemberMapper mem_mapper;
    public Map<String, Object> getCounts(){
        List<Integer> bookCntList = new ArrayList<Integer>();
        bookCntList.add(mapper.getTotalBookCnt());
        bookCntList.add(mapper.getAvailableBookCnt());
        bookCntList.add(mapper.getBorrowBookCnt());
        bookCntList.add(mapper.getOverdueBookCnt());
        bookCntList.add(mapper.getReserveBookCnt());
        bookCntList.add(mapper.getUnavailableBookCnt());

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

    public Map<String, Object> getUpdateDate(){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object> ();

        resultMap.put("book", mapper.getBookUpdatedate());
        resultMap.put("member", mapper.getMemberUpdatedate());
        return resultMap;
    }

    public void getUpdate(){
        ArrayList<Integer> mlist = new ArrayList<>();  //회원 중복 검사
        List<BorrowVO> uplist = borrow_mapper.checkDate(); //반납일 지난 대출 건 검색
        for(BorrowVO b:uplist){
            BookVO book = book_mapper.getBookInfoBySeq(b.getBbi_bi_seq());
            book.setBi_status(2);
            book_mapper.updateBook(book);  //도서 상태 연체중으로 변경

            MemberVO mem = mem_mapper.getMember(b.getBbi_mi_seq());
            if(!mlist.contains(mem.getMi_seq())){
                mlist.add(mem.getMi_seq());
                if(mem.getMi_status()==0) mem.setMi_status(1); 
                else if(mem.getMi_status()==1) mem.setMi_status(2); 
                mem_mapper.updateMember(mem); //회원상태 변경
            }
        }

        //정지회원 정지만료일 체크, 업뎃
    }
}
