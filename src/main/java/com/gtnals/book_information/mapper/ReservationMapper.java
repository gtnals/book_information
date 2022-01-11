package com.gtnals.book_information.mapper;

import java.util.List;

import com.gtnals.book_information.data.ReservationHistoryVO;
import com.gtnals.book_information.data.ReservationVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationMapper {
    public List<ReservationVO> getReservationList(Integer offset, String keyword, Integer key_opt, Integer order);
    public Integer getReservationCnt(String keyword, Integer key_opt);
    public Integer getReservationCntByBook(Integer seq);
    public Integer getReservationCntByMember(Integer seq);
    public Integer isExistReservation(Integer bi_seq, Integer mi_seq);
    public Integer isFirstReservation(Integer bi_seq, Integer mi_seq);
    public void addReservation(ReservationVO data);
    public void deleteReservation(Integer bri_seq);
    public void deleteFirstReservation(Integer bi_seq, Integer mi_seq);
    public void changePriority(Integer bi_seq);
    public void updateDuedate(Integer bi_seq);
    public ReservationVO getReservation(Integer seq);

    public List<ReservationVO> checkReservationInfo();

    public void insertReservationHistory(ReservationHistoryVO data);
    public Integer getRecentAddedReservationSeq();
    public Integer getRecentUpdatedReservationSeq();
}
