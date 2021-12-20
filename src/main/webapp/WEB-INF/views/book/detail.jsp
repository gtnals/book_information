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
    <link rel="stylesheet" href="/assets/css/book_detail.css">
    <script src="/assets/js/book_detail.js"></script>
</head>
<body>
    <main>
        <h1><i class="fas fa-book"></i> 도서 관리</h1>
        <p><i class="fas fa-info-circle"></i> 도서 상세정보</p>
        <div class="content_area">
            <h1>${book.data.bi_name}</h1>
            <div class="book_detail_info">
                <div class="image">
                    <img src="/assets/images/book.PNG" onerror="this.src='/assets/images/noimage.PNG'">
                    <p>이미지 출처: Naver 책정보</p>
                </div>
                <div class="detail_info">
                    <p>저자정보: ${book.data.author} 지음 
                        <c:if test="${book.data.bi_translator!=null}">
                            / ${book.data.bi_translator} 옮김 
                        </c:if>
                    </p><br>
                    <p>발행정보: ${book.data.bi_publisher} 
                        <c:if test="${book.data.bi_translator!=null}">
                            / ${book.data.bi_publication_date} 
                        </c:if>
                    </p><br>
                    <p>페이지: ${book.data.bi_page}p</p><br>
                    <p>분류: 
                        <c:if test="${book.data.bi_category==0}">총류(000)</c:if>
                        <c:if test="${book.data.bi_category==100}">철학(100)</c:if>
                        <c:if test="${book.data.bi_category==200}">종교(200)</c:if>
                        <c:if test="${book.data.bi_category==300}">사회학(300)</c:if>
                        <c:if test="${book.data.bi_category==400}">자연과학(400)</c:if>
                        <c:if test="${book.data.bi_category==500}">기술과학(500)</c:if>
                        <c:if test="${book.data.bi_category==600}">예술(600)</c:if>
                        <c:if test="${book.data.bi_category==700}">언어(700)</c:if>
                        <c:if test="${book.data.bi_category==800}">문학(800)</c:if>
                        <c:if test="${book.data.bi_category==900}">역사(900)</c:if>
                    </p><br>
                    <p>기타정보: 대출수 ${book.data.bi_cnt} / 추천수 ${book.data.bi_like}</p><br>
                </div>
            </div>
            <div class="table_area">
                <table>
                    <thead>
                        <tr>
                            <th>등록번호</th>
                            <th>청구번호</th>
                            <th>도서상태</th>
                            <th>반납예정일</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${book.data.bi_seq}</td>
                            <td>${book.data.bi_number}</td>
                            <td class="book_status">
                                <c:if test="${book.data.bi_status==0}">
                                    <span style="background-color: rgb(17,226,27);">대출가능</span>
                                </c:if>
                                <c:if test="${book.data.bi_status==1}">
                                    <span style="background-color: rgb(255, 23, 23);">대출중</span>
                                </c:if>
                                <c:if test="${book.data.bi_status==2}">
                                    <span style="background-color: rgb(255, 110, 26);">연체중</span>
                                </c:if>
                                <c:if test="${book.data.bi_status==3}">
                                    <span style="background-color: rgb(251, 186, 64);">예약중</span>
                                </c:if>
                                <c:if test="${book.data.bi_status==4}">
                                    <span style="background-color: rgb(128, 107, 109);">이용불가</span>
                                </c:if>
                            </td>
                            <c:if test="${book.data.bi_status==1}"><td>2021-12-30</td></c:if>
                            <c:if test="${book.data.bi_status!=1}"><td></td></c:if>
                            <c:if test="${book.data.bi_status!=4}">
                                <td><button id="reserve_btn">예약하기(1/2)</button></td>
                            </c:if>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="button_area">
                <button id="review_btn">리뷰 작성</button>
                <button id="list_btn">목록으로</button>
                <button id="like_btn"><i class="far fa-thumbs-up"></i> 추천 </button>
            </div>
            <div class="review_area">
                <p><i class="fas fa-comment-alt"></i> 리뷰</p>
                <table>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>추천수</th>
                            <th>작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                        </tr>
                        <tr>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                        </tr>
                        <tr>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                        </tr>
                        <tr>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                        </tr>
                        <tr>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                            <td>하나</td>
                        </tr>
                    </tbody>
                </table>
                <div class="pager_area">
                    <button id="prev"><i class="fas fa-chevron-left"></i></button>
                    <div class="pagers">
                        <c:forEach begin="1" end="${data.pageCnt}" var="i">
                            <c:if test="${data.key_opt!=3}">
                                <a href="#">${i}</a>
                            </c:if>
                        </c:forEach>
                    </div>
                    <button id="next"><i class="fas fa-chevron-right"></i></button>
                </div>
            </div>
        </div>
    </main>
</body>
</html>