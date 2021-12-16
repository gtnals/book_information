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
    <script src="/assets/js/book.js"></script>
    <script src="/assets/js/book_detail.js"></script>
</head>
<body>
    <main>
        <h1><i class="fas fa-book"></i> 도서 관리</h1>
        <p>도서 상세정보</p>
        <div class="content_area">
            <h1>귤의 맛</h1>
            <div class="book_detail_info">
                <div class="image">
                    <img src="/assets/image/book1.PNG">
                    <p>이미지 출처: Naver 책정보</p>
                </div>
                <div class="detail_info">
                    <p>저자정보: 조남주 지음 / 누가 옮김 </p><br>
                    <p>발행정보: 누구출판사 / 2012 </p><br>
                    <p>페이지: 272p</p><br>
                    <p>분류: 한국문학</p><br>
                    <p>기타정보: 대출수100 / 추천수40</p><br>
                </div>
                <!-- <div class="sub_info">
                    새콤달콤 뀰<br>
                    맛있는 뀰 <br>
                    뀰뀰뀰
                </div> -->
            </div>
            <div class="table_area">
                <table>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>등록번호</th>
                            <th>청구번호</th>
                            <th>도서상태</th>
                            <th>반납예정일</th>
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
                    </tbody>
                </table>
            </div>
            <button id="">예약신청</button>
            <button id="">추천</button>
        </div>
    </main>
</body>
</html>