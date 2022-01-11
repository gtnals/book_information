package com.gtnals.book_information.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gtnals.book_information.data.BookHistoryVO;
import com.gtnals.book_information.data.BookVO;
import com.gtnals.book_information.data.BorrowVO;
import com.gtnals.book_information.data.MemberHistoryVO;
import com.gtnals.book_information.data.MemberVO;
import com.gtnals.book_information.data.ReservationHistoryVO;
import com.gtnals.book_information.data.ReservationVO;
import com.gtnals.book_information.data.SuspendHistoryVO;
import com.gtnals.book_information.data.SuspendVO;
import com.gtnals.book_information.mapper.BookMapper;
import com.gtnals.book_information.mapper.BorrowMapper;
import com.gtnals.book_information.mapper.DashboardMapper;
import com.gtnals.book_information.mapper.MemberMapper;
import com.gtnals.book_information.mapper.ReservationMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired DashboardMapper mapper;
    @Autowired BookMapper book_mapper;
    @Autowired BorrowMapper borrow_mapper;
    @Autowired MemberMapper mem_mapper;
    @Autowired ReservationMapper reserve_mapper;
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
        boardCntList.add(mapper.getWaitBoardCnt());
        boardCntList.add(mapper.getCompleteBoardCnt());

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
        resultMap.put("board", mapper.getBoardUpdatedate());
        return resultMap;
    }

    public void getUpdate(){
        ArrayList<Integer> mlist = new ArrayList<>();  
        List<BorrowVO> uplist = borrow_mapper.checkDate(); //반납일 지난 대출 건 검색(연체 처리)
        for(BorrowVO b:uplist){
            BookVO book = book_mapper.getBookInfoBySeq(b.getBbi_bi_seq());
            book.setBi_status(2);
            book_mapper.updateBook(book);  //도서 상태 연체중으로 변경
            
            BookHistoryVO history = new BookHistoryVO();
            history.setBh_bi_seq(book.getBi_seq());
            history.setBh_type("update");
            String content = book.getBi_name()+"|"+book.getBi_number()+"|"+book.getBi_ai_seq()+"|"+book.getBi_status()+"|"+
                book.getBi_publisher()+"|"+book.getBi_category()+"|"+book.getBi_publication_date()+"|"+book.getBi_page()+"|"+
                book.getBi_image();
            history.setBh_content(content);
            book_mapper.insertBookHistory(history);

            MemberVO mem = mem_mapper.getMember(b.getBbi_mi_seq());
            if(!mlist.contains(mem.getMi_seq())){
                mlist.add(mem.getMi_seq());
                if(mem.getMi_status()==0) mem.setMi_status(1); 
                else if(mem.getMi_status()==1) mem.setMi_status(2); 
                mem_mapper.updateMember(mem); //회원상태 변경

                MemberHistoryVO mhistory = new MemberHistoryVO();
                mhistory.setMih_type("modify");
                mhistory.setMih_mi_content(mem.makeHistoryStr());
                mhistory.setMih_mi_seq(mem.getMi_seq());
                mem_mapper.insertMemberHistory(mhistory);
            }
        }
        //정지만료일 지난 정지 정보 삭제
        List<SuspendVO> slist = mem_mapper.checkSuspendInfo();  //만료일 지난 정지정보들
        for(SuspendVO s:slist){
            Integer mi_seq = s.getSi_mi_seq();
            if(mem_mapper.getSuspendCntBymem(mi_seq)==1){   //정지 정보가 1개 뿐이면
                MemberVO mem = mem_mapper.getMember(mi_seq);
                mem.setMi_status(0);    
                mem_mapper.updateMember(mem);   //정상 회원으로 변경

                //*회원 로그 추가(업뎃에 대한)
                MemberHistoryVO memhistory = new MemberHistoryVO();
                memhistory.setMih_type("modify");
                memhistory.setMih_mi_content(mem.makeHistoryStr());
                memhistory.setMih_mi_seq(mem.getMi_seq());
                mem_mapper.insertMemberHistory(memhistory);
            }
            mem_mapper.deleteSuspendInfo(s.getSi_seq());

            SuspendHistoryVO history = new SuspendHistoryVO();
            history.setSh_si_seq(s.getSi_seq());
            history.setSh_type("delete");
            mem_mapper.insertSuspendHistory(history);            
        }
        //예약만료일 지난 예약 정보 삭제
        List<ReservationVO> rlist = reserve_mapper.checkReservationInfo(); //만료일 지난 예약정보들
        for(ReservationVO r: rlist){
            Integer bi_seq = r.getBri_bi_seq();
            //해당 도서에 다른 예약 없으면 도서상태 대출가능(0)으로 변경
            if(reserve_mapper.getReservationCntByBook(bi_seq)==1){
                BookVO book = book_mapper.getBookInfoBySeq(bi_seq);
                book.setBi_status(0);
                book_mapper.updateBook(book);
                //*도서 로그 추가 (업뎃에 대한)
                BookHistoryVO bhistory = new BookHistoryVO();
                bhistory.setBh_bi_seq(book.getBi_seq());
                bhistory.setBh_type("update");
                String content = book.getBi_name()+"|"+book.getBi_number()+"|"+book.getBi_ai_seq()+"|"+book.getBi_status()+"|"+
                    book.getBi_publisher()+"|"+book.getBi_category()+"|"+book.getBi_publication_date()+"|"+book.getBi_page()+"|"+
                    book.getBi_image();
                bhistory.setBh_content(content);
                book_mapper.insertBookHistory(bhistory);
            }
            //다른 예약(대기번호 2) 있으면 1로 변경 및 예약만료일 지정
            else{
                reserve_mapper.changePriority(bi_seq);
                reserve_mapper.updateDuedate(bi_seq);

                //*예약 로그 추가 (업뎃에 대한)
                ReservationHistoryVO rhistory = new ReservationHistoryVO();
                ReservationVO rdata = reserve_mapper.getReservation(reserve_mapper.getRecentUpdatedReservationSeq());
                rhistory.setBrh_bri_seq(rdata.getBri_seq());
                rhistory.setBrh_type("update");
                rhistory.setBrh_content(rdata.makeHistoryStr());
                reserve_mapper.insertReservationHistory(rhistory);
            }
            reserve_mapper.deleteReservation(r.getBri_seq());   //예약정보 삭제
            
            ReservationHistoryVO history = new ReservationHistoryVO();
            history.setBrh_bri_seq(r.getBri_seq());
            history.setBrh_type("delete");
            reserve_mapper.insertReservationHistory(history);
        }
    }
}
