<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <%@include file="/WEB-INF/includes/header.jsp"%>
    <link rel="stylesheet" href="/assets/css/book_list.css">
    <link rel="stylesheet" href="/assets/css/borrow_list.css">
    <link rel="stylesheet" href="/assets/css/reservation_list.css">
    <script src="/assets/js/reservation.js"></script>
</head>
<body>
    <main>
        <h1><i class="fas fa-bookmark"></i> 예약 관리</h1>
        <button id="add_book"><i class="fas fa-plus-circle"></i> 예약 정보 추가</button>
        <div class="content_area">
            <div class="menu_area">
                <div class="search_box">
                    <select id="search_opt">
                        <option value="0" <c:if test="${data.key_opt==0}">selected</c:if> >전체</option>
                        <option value="1" <c:if test="${data.key_opt==1}">selected</c:if> >청구번호</option>
                        <option value="2" <c:if test="${data.key_opt==2}">selected</c:if> >회원ID</option>
                    </select>
                    <input type="text" id="keyword" placeholder="검색어 입력" value="${data.keyword}">
                    <button id="search_btn"><i class="fas fa-search"></i></button>
                </div>
                <div class="search_result" 
                    <c:if test="${data.keyword!=''}">
                        <p>*총 ${data.total}건이 검색되었습니다.</p>
                    </c:if>
                </div>
            </div>
            <div class="order_select">
                <select id="order" onchange="orderList()">
                    <option value="0" <c:if test="${data.order=='0'}">selected</c:if> >최신순</option>
                    <option value="1" <c:if test="${data.order=='1'}">selected</c:if> >만료일순</option>
                    <option value="2" <c:if test="${data.order=='2'}">selected</c:if> >회원ID순</option>
                    <option value="3" <c:if test="${data.order=='3'}">selected</c:if> >청구번호순</option>
                </select>
            </div>
            <div class="table_area">
                <table>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>이름(회원ID)</th>
                            <th>청구번호</th>
                            <th>대기번호</th>
                            <th>예약만료일</th>
                            <th>등록일</th>
                            <th>수정일</th>
                            <th>조작</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${data.total==0}">
                            <tr>
                                <td id="nodata" colspan="8">데이터가 없습니다.</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${data.list}" var="d">
                            <tr>
                                <td>${d.bri_seq}</td>
                                <fmt:formatNumber var="phone" value="${d.mem_phone}" pattern="###,####,####"/>
                                <td title=<c:out value="0${fn:replace(phone, ',', '-')}" /> >${d.mem_name} (${d.mem_id})</td>
                                <td title="${d.book_name}">${d.book_num}</td>
                                <td>${d.bri_priority}</td>
                                <td><fmt:formatDate value="${d.bri_due_date}" pattern="yyyy-MM-dd (EE)"/></td>
                                <td><fmt:formatDate value="${d.bri_reg_dt}" pattern="yyyy-MM-dd (EE) HH:mm:ss"/></td>
                                <td><fmt:formatDate value="${d.bri_mod_dt}" pattern="yyyy-MM-dd (EE) HH:mm:ss"/></td>
                                <td>
                                    <button class="delete_btn" days="${endDate - strDate}" mi_seq="${d.bri_mi_seq}" bi_seq="${d.bri_bi_seq}" data-seq="${d.bri_seq}"><i class="fas fa-minus-circle"></i></button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="pager_area">
                <button id="prev"><i class="fas fa-chevron-left"></i></button>
                <div class="pagers">
                    <c:forEach begin="1" end="${data.pageCnt}" var="i">
                        <a href="/reservation?offset=${(i-1)*10}&keyword=${data.keyword}&order=${data.order}">${i}</a>
                    </c:forEach>
                </div>
                <button id="next"><i class="fas fa-chevron-right"></i></button>
            </div>
        </div>
    </main>
    <div class="popup_wrap">
        <div class="popup" id="book_add">
            <div class="top_area">
                <div class="ico">
                    <i class="fas fa-bookmark"></i>
                </div>
                <h2>예약 정보 추가</h2>
                <p>예약 정보를 입력해주세요</p>
            </div>
            <div class="content_area">
                <input type="text" id="bb_mem_id" placeholder="회원명 (회원ID)" disabled>
                <button id="search_mem">회원검색</button>
                <input type="text" id="bb_book_num" placeholder="도서명 (청구번호)" disabled>
                <button id="search_bo">도서검색</button>
                <div class="bo_date">
                    <p>예약 신청일</p>
                    <c:set var="today"><fmt:formatDate value='${today}' pattern='yyyy-MM-dd' /></c:set>
                    <input type="text" id="bb_borrow_date" value="${borrow_date}" disabled>
                </div>
                <div class="due_date">
                    <p>대기번호</p>
                    <input type="text" id="bb_due_date" value="도서를 선택하세요." disabled>
                </div>
            </div>
            <div class="btn_area">
                <button id="add_bb">등록하기</button>
                <button id="cancel_bb">취소하기</button>
            </div>
        </div>
    </div>
    <div class="department_search">
        <div class="dep_search_box">
            <input type="text" id="mem_keyword" placeholder="회원 이름 입력">
            <input type="text" id="book_keyword" placeholder="도서 이름 입력">
            <button id="mem_search_btn"><i class="fas fa-search"></i></button>
            <button id="book_search_btn"><i class="fas fa-search"></i></button>
        </div>
        <div class="result">
            <ul>

            </ul>
        </div>
        <div class="dep_search_buttons">
            <button id="dep_search_close">닫기</button>
        </div>
    </div>
</body>
</html>