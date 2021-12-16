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
    <link rel="stylesheet" href="/assets/css/member_list.css">
    <script src="/assets/js/book.js"></script>
    <script src="/assets/js/member.js"></script>
</head>
<body>
    <main>
        <h1><i class="fas fa-user"></i> 회원 관리</h1>
        <button id="add_book"><i class="fas fa-plus-circle"></i> 회원 추가</button>
        <div class="content_area">
            <div class="menu_area">
                <div class="search_box">
                    <select id="search_opt">
                        <option value="0" <c:if test="${data.key_opt==0}">selected</c:if> >전체</option>
                        <option value="1" <c:if test="${data.key_opt==1}">selected</c:if> >도서명</option>
                        <option value="2" <c:if test="${data.key_opt==2}">selected</c:if> >저자명</option>
                    </select>
                    <input type="text" id="keyword" placeholder="검색어 입력" value="${data.keyword}">
                    <button id="search_btn"><i class="fas fa-search"></i></button>
                </div>
                <button id="detail_btn">상세 검색</button>
                <button id="close_btn">닫기</button>
                <div class="detail_search_box" style="display: none;">
                    <div class="search_box">
                        <label for="key_b_name">도서명: </label>
                        <input type="text" id="key_b_name" value="${data.key_b_name}">
                    </div>
                    <div class="search_box">
                        <label for="key_b_author">저자: </label>
                        <input type="text" id="key_b_author" value="${data.key_b_author}">
                    </div>
                    <div class="search_box">
                        <label for="key_b_publisher">발행처: </label>
                        <input type="text" id="key_b_publisher" value="${data.key_b_publisher}">
                    </div>
                    <div class="search_box">
                        <label for="key_b_category">분류: </label>
                        <select id="key_b_category">
                            <option value="-1" <c:if test="${data.key_b_category==-1}">selected</c:if> >분류없음</option>
                            <option value="0" <c:if test="${data.key_b_category==0}">selected</c:if> >총류(000)</option>
                            <option value="100" <c:if test="${data.key_b_category==100}">selected</c:if> >철학(100)</option>
                            <option value="200" <c:if test="${data.key_b_category==200}">selected</c:if> >종교(200)</option>
                            <option value="300" <c:if test="${data.key_b_category==300}">selected</c:if> >사회학(300)</option>
                            <option value="400" <c:if test="${data.key_b_category==400}">selected</c:if> >언어(400)</option>
                            <option value="500" <c:if test="${data.key_b_category==500}">selected</c:if> >자연과학(500)</option>
                            <option value="600" <c:if test="${data.key_b_category==600}">selected</c:if> >기술과학(600)</option>
                            <option value="700" <c:if test="${data.key_b_category==700}">selected</c:if> >예술(700)</option>
                            <option value="800" <c:if test="${data.key_b_category==800}">selected</c:if> >문학(800)</option>
                            <option value="900" <c:if test="${data.key_b_category==900}">selected</c:if> >역사(900)</option>
                        </select>
                    </div>
                    <button id="detail_search_btn">검색</button>
                    <button id="reset_btn">초기화</button>
                </div>
                <!-- <p>*총 ${data.total}권이 검색되었습니다.</p> -->
            </div>
            <div class="order_select">
                <select id="order">
                    <option value="0">최신순</option>
                    <option value="0">대출순</option>
                    <option value="0">추천순</option>
                    <option value="0">이름순</option>
                </select>
            </div>
            <div class="table_area">
                <table>
                    <thead>
                        <tr>
                            <th>등록번호</th>
                            <th>청구번호</th>
                            <th>도서명</th>
                            <th>저자</th>
                            <th>도서상태</th>
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
                                <td>${d.bi_seq}</td>
                                <td>${d.bi_number}</td>
                                <td><button id="book_detail">${d.bi_name}</button></td>
                                <td><button id="author_detail">${d.author}</button></td>
                                <c:if test="${d.bi_status==0}">
                                    <td style="color: blue;font-weight: 600;">대출가능</td>
                                </c:if>
                                <c:if test="${d.bi_status==1}">
                                    <td style="color: red;font-weight: 600;">대출중</td>
                                </c:if>
                                <c:if test="${d.bi_status==2}">
                                    <td style="color: darkred;font-weight: 600;">연체중</td>
                                </c:if>
                                <c:if test="${d.bi_status==3}">
                                    <td style="color: coral;font-weight: 600;">예약중</td>
                                </c:if>
                                <c:if test="${d.bi_status==4}">
                                    <td style="color: deeppink;font-weight: 600;">이용불가</td>
                                </c:if>
                                <td>${d.bi_reg_dt}</td>
                                <td>${d.bi_mod_dt}</td>
                                <td>
                                    <button class="modify_btn" data-seq="${d.bi_seq}"><i class="fas fa-pencil-alt"></i></button>
                                    <button class="delete_btn" data-seq="${d.bi_seq}"><i class="fas fa-minus-circle"></i></button>
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
                            <a href="/book?offset=${(i-1)*10}&keyword=${data.keyword}">${i}</a>
                        </c:if>
                        <c:if test="${data.key_opt==3}">
                            <a href="/book?offset=${(i-1)*10}&name=${data.key_b_name}&author=${data.key_b_author}&publisher=${data.key_b_publisher}&category=${data.key_b_category}">${i}</a>
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
                <h2>도서 추가</h2>
                <p>도서 정보를 입력해주세요</p>
            </div>
            <div class="content_area">
                <input type="text" id="b_name" placeholder="도서명"><br>
                <input type="text" id="b_number" placeholder="청구번호">
                <select id="b_status">
                    <option value="0">대출가능</option>
                    <option value="1">대출중</option>
                    <option value="2">연체중</option>
                    <option value="3">예약중</option>
                    <option value="4">이용불가</option>
                </select>
                <div class="author_search_box">
                    <input type="text" id="author_name" placeholder="저자명">
                    <button id="author_search_btn"><i class="fas fa-search"></i></button>
                </div>
                <select id="b_author">
                    <option value="0">저자명 입력</option>
                </select>
                <input type="text" id="b_publisher" placeholder="발행처">
                <select id="b_category">
                    <option value="0">총류(000)</option>
                    <option value="100">철학(100)</option>
                    <option value="200">종교(200)</option>
                    <option value="300">사회학(300)</option>
                    <option value="400">언어(400)</option>
                    <option value="500">자연과학(500)</option>
                    <option value="600">기술과학(600)</option>
                    <option value="700">예술(700)</option>
                    <option value="800">문학(800)</option>
                    <option value="900">역사(900)</option>
                </select>
            </div>
            <div class="btn_area">
                <button id="add_b">등록하기</button>
                <button id="mod_b">수정하기</button>
                <button id="cancel_b">취소하기</button>
            </div>
        </div>
    </div>
</body>
</html>