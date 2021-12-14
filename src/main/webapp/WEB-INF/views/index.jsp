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
    <link rel="stylesheet" href="/assets/css/index.css">
    <script>
        $(function(){
            $(".main_menu a:nth-child(1)").addClass("active");
        })
    </script>
</head>
<body>
    <main>
        <h1>도서정보 대시보드 (Book information dashboard)</h1>
        <div class="content_area">
            <div class="book_info">
                <h2><i class="fas fa-book"></i> 도서 정보</h2>
                <p>총 등록 도서 : <span>${cnt.book[0]}건</span></p>
                <p>대출 가능 도서 : <span>${cnt.book[1]}건</span></p>
                <p>대출 중 도서 : <span>${cnt.book[2]}건</span></p>
                <p>연체 중 도서 : <span>${cnt.book[3]}건</span></p>
                <p>예약 중 도서 : <span>${cnt.book[4]}건</span></p>
                <p>이용 불가 도서 : <span>${cnt.book[5]}건</span></p>
                <p><i class="fas fa-clock"></i> 업데이트 날짜 : <span>2021-12-09 12:00:00</span></p>
            </div>
            <div class="member_info">
                <h2><i class="fas fa-user"></i> 회원 정보</h2>
                <p>총 등록 회원 : <span>${cnt.member[0]}명</span></p>
                <p>정상 회원 : <span>${cnt.member[1]}명</span></p>
                <p>경고 회원 : <span>${cnt.member[2]}명</span></p>
                <p>정지 회원 : <span>${cnt.member[3]}명</span></p>
                <p><i class="fas fa-clock"></i> 업데이트 날짜 : <span>2021-12-09 12:00:00</span></p>
            </div>
            <div class="review_info">
                <h2><i class="fas fa-comment-alt"></i> 리뷰 정보</h2>
                <p>총 등록 리뷰 : <span>${cnt.review[0]}건</span></p>
                <p>비공개 리뷰 : <span>${cnt.review[1]}건</span></p>
                <p>정상 리뷰 : <span>${cnt.review[2]}건</span></p>
                <p>신고 접수 리뷰 : <span>${cnt.review[3]}건</span></p>
                <p><i class="fas fa-clock"></i> 업데이트 날짜 : <span>2021-12-09 12:00:00</span></p>
            </div>
            <div class="member_board_info">
                <h2><i class="fas fa-file-alt"></i> 게시판 정보</h2> <!-- 1 -->
                <p>총 등록 글 : <span>${cnt.board[0]}건</span></p> <!-- 2 -->
                <p class="small">-답변 대기 : <span>${cnt.board[4]}건</span></p> <!-- 3 -->
                <p class="small">-답변 완료 : <span>${cnt.board[5]}건</span></p>
                <p>도서 신청 글 : <span>${cnt.board[1]}건</span></p>
                <p>도서 훼손 신고 글 : <span>${cnt.board[2]}건</span></p>
                <p>기타 글 : <span>${cnt.board[3]}건</span></p>
                <p><i class="fas fa-clock"></i> 업데이트 날짜 : <span>2021-12-09 12:00:00</span></p>
            </div>
        </div>
    </main>
</body>
</html>