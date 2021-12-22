package com.gtnals.book_information.mapper;

import java.util.List;

import com.gtnals.book_information.data.MemberHistoryVO;
import com.gtnals.book_information.data.MemberVO;
import com.gtnals.book_information.data.SuspendVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public List<MemberVO> getMemberInfo(Integer offset, String keyword, Integer key_opt, Integer order); 
    public Integer getMemberCnt(String keyword, Integer key_opt);
    public void addMemberInfo(MemberVO data);
    public void deleteMember(Integer seq);
    public MemberVO getMember(Integer seq);
    public void updateMember(MemberVO data);

    public MemberVO getMemberByID(String mi_id);

    public void insertMemberHistory(MemberHistoryVO data);
    public Integer getRecentAddedMemberSeq();

    public List<MemberVO> getMemberbyKeyword(String keyword);

    public void addSuspendInfo(Integer si_mi_seq, Integer days);
}   
