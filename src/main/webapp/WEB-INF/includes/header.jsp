<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/assets/css/reset.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <link rel="stylesheet" href="/assets/css/header.css">
</head>
<body>
    <header>
        <div class="menu_wrap">
            <div class="user_menu">
                <a href="#" id="profile_img">
                    <img src="http://placekitten.com/100/100">
                    <span>수정하기</span>
                </a>
                <a href="#" id="user_name">관리자(admin)</a>
                <a href="#" id="logout"><i class="fas fa-sign-out-alt"></i> 로그아웃</a>
            </div>
            <div class="main_menu">
                <a href="/"><i class="fas fa-columns"></i> 대시보드</a>
                <a href="/book"><i class="fas fa-book"></i> 도서 관리</a>
                <a href="/borrow"><i class="fas fa-book-open"></i> 대출 관리</a>
                <a href="/reservation"><i class="fas fa-bookmark"></i> 예약 관리</a>
                <a href="/member"><i class="fas fa-user"></i> 회원 관리</a>
                <a href="/board"><i class="fas fa-list-ul"></i> 게시판 관리</a>
            </div>
        </div>
    </header>
</body>
</html>