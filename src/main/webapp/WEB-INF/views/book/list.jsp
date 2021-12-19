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
    <script src="/assets/js/book.js"></script>
</head>
<body>
    <main>
        <h1><i class="fas fa-book"></i> 도서 관리</h1>
        <button id="add_book"><i class="fas fa-plus-circle"></i> 도서 추가</button>
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
                <div class="detail_search_box" 
                    <c:if test="${data.key_opt!=3}">
                        style="display: none;"
                    </c:if>
                    ><div class="icon_close">
                        <button id="detail_close_btn"><i class="fas fa-window-close"></i></button>
                    </div>
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
                            <option value="400" <c:if test="${data.key_b_category==400}">selected</c:if> >자연과학(400)</option>
                            <option value="500" <c:if test="${data.key_b_category==500}">selected</c:if> >기술과학(500)</option>
                            <option value="600" <c:if test="${data.key_b_category==600}">selected</c:if> >예술(600)</option>
                            <option value="700" <c:if test="${data.key_b_category==700}">selected</c:if> >언어(700)</option>
                            <option value="800" <c:if test="${data.key_b_category==800}">selected</c:if> >문학(800)</option>
                            <option value="900" <c:if test="${data.key_b_category==900}">selected</c:if> >역사(900)</option>
                        </select>
                    </div>
                    <button id="detail_search_btn">검색</button>
                    <button id="reset_btn">초기화</button>
                </div>
                <div class="search_result" 
                    <c:if test="${data.keyword!=''||data.key_b_name!=''||data.key_b_author!=''||data.key_b_publisher!=''||data.key_b_category!=-1}">
                        <p>*총 ${data.total}권이 검색되었습니다.</p>
                    </c:if>
                </div>
            </div>
            <div class="order_select">
                <select id="order" onchange="orderList('${data.key_opt}')">
                    <option value="0" <c:if test="${data.order=='0'}">selected</c:if> >최신순</option>
                    <option value="1" <c:if test="${data.order=='1'}">selected</c:if> >대출순</option>
                    <option value="2" <c:if test="${data.order=='2'}">selected</c:if> >추천순</option>
                    <option value="3" <c:if test="${data.order=='3'}">selected</c:if> >제목순</option>
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
                                <td><a href="/book/detail?bi_seq=${d.bi_seq}">${d.bi_name} <i class="fas fa-search-plus"></i></a></td>
                                <td><button class="author_detail_btn" data-seq="${d.bi_ai_seq}">${d.author} <i class="fas fa-pencil-alt"></i></button></td>
                                <td class="book_status"> 
                                    <c:if test="${d.bi_status==0}">
                                        <span style="background-color: rgb(17,226,27);">대출가능</span>
                                    </c:if>
                                    <c:if test="${d.bi_status==1}">
                                        <span style="background-color: rgb(255, 23, 23);">대출중</span>
                                    </c:if>
                                    <c:if test="${d.bi_status==2}">
                                        <span style="background-color: rgb(255, 110, 26);">연체중</span>
                                    </c:if>
                                    <c:if test="${d.bi_status==3}">
                                        <span style="background-color: rgb(251, 186, 64);">예약중</span>
                                    </c:if>
                                    <c:if test="${d.bi_status==4}">
                                        <span style="background-color: rgb(128, 107, 109);">이용불가</span>
                                    </c:if>
                                </td>
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
                <h2>도서 추가</h2>
                <p>도서 정보를 입력해주세요</p>
                <button id="add_author"><i class="fas fa-plus-circle"></i> 저자 추가</button>
            </div>
            <div class="content_area">
                <input type="text" id="b_name" placeholder="도서명">
                <select id="b_status">
                    <option value="0">대출가능</option>
                    <option value="1">대출중</option>
                    <option value="2">연체중</option>
                    <option value="3">예약중</option>
                    <option value="4">이용불가</option>
                </select>
                <input type="text" id="b_number" placeholder="청구번호">
                <select id="b_category">
                    <option value="0">총류(000)</option>
                    <option value="100">철학(100)</option>
                    <option value="200">종교(200)</option>
                    <option value="300">사회학(300)</option>
                    <option value="400">자연과학(400)</option>
                    <option value="500">기술과학(500)</option>
                    <option value="600">예술(600)</option>
                    <option value="700">언어(700)</option>
                    <option value="800">문학(800)</option>
                    <option value="900">역사(900)</option>
                </select>
                <div class="author_search_box">
                    <input type="text" id="author_name" placeholder="지은이">
                    <button id="author_search_btn"><i class="fas fa-search"></i></button>
                </div>
                <select id="b_author">
                    <option value="0">저자코드</option>
                </select>
                
                <input type="text" id="b_translator" placeholder="옮긴이">
                <input type="number" id="b_page" placeholder="페이지수">
                <input type="text" id="b_image" placeholder="이미지">
                <input type="text" id="b_publisher" placeholder="발행처">
                <input type="text" id="b_publication_date" placeholder="발행일(YYYYMMDD)">
            </div>
            <div class="btn_area">
                <button id="add_b">등록하기</button>
                <button id="mod_b">수정하기</button>
                <button id="cancel_b">취소하기</button>
            </div>
        </div>
    </div>
    <div class="add_author">
        <div class="top_area">
            <p><i class="fas fa-feather"></i> 저자 추가</p>
        </div>
        <div class="content_area">
            <input type="text" id="a_name" placeholder="저자명">
            <input type="text" id="a_number" placeholder="저자코드">
            <input type="text" id="a_phone" placeholder="전화번호(01012345678)">
            <input type="text" id="a_email" placeholder="이메일">
            <input type="text" id="a_insta" placeholder="인스타ID">
        </div>
        <div class="btn_area">
            <button id="add_a">등록</button>
            <button id="mod_a">수정</button>
            <button id="cancel_a">취소</button>
        </div>
    </div>
</body>
</html>