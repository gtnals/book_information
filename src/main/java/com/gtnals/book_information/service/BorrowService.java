package com.gtnals.book_information.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.gtnals.book_information.data.BorrowVO;
import com.gtnals.book_information.mapper.BookMapper;
import com.gtnals.book_information.mapper.BorrowMapper;
import com.gtnals.book_information.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowService {
    @Autowired BorrowMapper mapper;
    @Autowired MemberMapper m_mapper;
    @Autowired BookMapper b_mapper;
    public Map<String, Object> addBorrow(BorrowVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Integer bbi_bi_seq = b_mapper.getBookInfoByNum(data.getBbi_book_num()).getBi_seq();
        Integer bbi_mi_seq = m_mapper.getMemberByID(data.getBbi_mem_id()).getMi_seq();
        data.setBbi_bi_seq(bbi_bi_seq);
        data.setBbi_mi_seq(bbi_mi_seq);
        mapper.addBorrow(data);
        // 미완
        return resultMap;
    }
}
