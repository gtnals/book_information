package com.gtnals.book_information.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DashboardMapper {
    public Integer getTotalBookCnt();
    public Integer getAvailableBookCnt();
    public Integer getBorrowBookCnt();
    public Integer getOverdueBookCnt();
    public Integer getReserveBookCnt();

    public Integer getTotalMemberCnt();
    public Integer getNormalMemberCnt();
    public Integer getWarningMemberCnt();
    public Integer getDeactiveMemberCnt();

    public Integer getTotalReviewCnt();
    public Integer getPrivateReviewCnt();
    public Integer getNormalReviewCnt();
    public Integer getwaitDeleteCnt();

    public Integer getTotalBoardCnt();
    public Integer getAddBookBoardCnt();
    public Integer getReportBookBoardCnt();
    public Integer getEtcBoardCnt();
    public Integer getCompleteBoardCnt();
    public Integer getWaitBoardCnt();
}
