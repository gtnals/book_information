<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <script src="/assets/js/borrow.js"></script>
</head>
<body>
    <main>
        <h1><i class="fas fa-book-open"></i> 대출 관리</h1>
        <button id="add_book"><i class="fas fa-plus-circle"></i> 대출</button>
        <div class="content_area">
            <div class="menu_area">
                <div class="search_box">
                    <select id="search_opt">
                        <option value="0" <c:if test="${data.key_opt==0}">selected</c:if> >전체</option>
                        <option value="1" <c:if test="${data.key_opt==1}">selected</c:if> >도서명</option>
                        <option value="2" <c:if test="${data.key_opt==2}">selected</c:if> >회원명</option>
                    </select>
                    <input type="text" id="keyword" placeholder="검색어 입력" value="${data.keyword}">
                    <button id="search_btn"><i class="fas fa-search"></i></button>
                </div>
                <button id="detail_btn" overdue_check="0">연체도서만 표시</button>
                <div class="search_result" 
                    <c:if test="${data.keyword!=''||data.overdue_check==1}">
                        <p>*총 ${data.total}건이 검색되었습니다.</p>
                    </c:if>
                </div>
            </div>
            <div class="order_select">
                <select id="order" onchange="orderList('${data.key_opt}')">
                    <option value="0" <c:if test="${data.order=='0'}">selected</c:if> >최신순</option>
                    <option value="1" <c:if test="${data.order=='1'}">selected</c:if> >반납일순</option>
                    <option value="2" <c:if test="${data.order=='2'}">selected</c:if> >회원ID순</option>
                    <option value="3" <c:if test="${data.order=='3'}">selected</c:if> >청구번호순</option>
                </select>
            </div>
            <div class="table_area">
                <table>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>회원ID</th>
                            <th>청구번호</th>
                            <th>대출일</th>
                            <th>반납일</th>
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
                                <td>${d.bbi_seq}</td>
                                <td>${d.bbi_mem_id}</td>
                                <td>${d.bbi_book_num}</td>
                                <td>${d.bbi_borrow_date}</td>
                                <td>${d.bbi_due_date}</td>
                                <td>${d.bbi_reg_dt}</td>
                                <td>${d.bbi_mod_dt}</td>
                                <td>
                                    <button class="modify_btn" data-seq="${d.bbi_seq}"><i class="fas fa-pencil-alt"></i></button>
                                    <button class="delete_btn" data-seq="${d.bbi_seq}"><i class="fas fa-minus-circle"></i></button>
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
                        <c:if test="${data.key_opt!=3}">
                            <a href="/book?offset=${(i-1)*10}&keyword=${data.keyword}&order=${data.order}">${i}</a>
                        </c:if>
                        <c:if test="${data.key_opt==3}">
                            <a href="/book?key_opt=3&offset=${(i-1)*10}&name=${data.key_b_name}&author=${data.key_b_author}&publisher=${data.key_b_publisher}&category=${data.key_b_category}&order=${data.order}">${i}</a>
                        </c:if>
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
                    <i class="fas fa-book"></i>
                </div>
                <h2>대출</h2>
                <p>대출 정보를 입력해주세요</p>
            </div>
            <div class="content_area">
                <input type="text" id="bb_mem_id" placeholder="회원ID">
                <input type="text" id="bb_book_num" placeholder="도서 청구번호">
                <input type="text" id="bb_borrow_date" placeholder="대출일(YYYYMMDD)">
                <input type="text" id="bb_due_date" placeholder="반납일(YYYYMMDD)">

                <!-- <input type="text" id="bb_borrow_date" value="${borrow_date}" disabled>
                <input type="text" id="bb_due_date" value="${due_date}" disabled> -->
            </div>
            <div class="btn_area">
                <button id="add_bb">등록하기</button>
                <button id="mod_bb">수정하기</button>
                <button id="cancel_bb">취소하기</button>
            </div>
        </div>
    </div>
</body>
</html>