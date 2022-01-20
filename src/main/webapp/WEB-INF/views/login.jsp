<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="/assets/css/login.css">
</head>
<body>
    <main>
        <h1>도서정보시스템</h1>
        <div class="content_area">
            <div class="login_form">
                <h1>관리자 로그인</h1>
                <form action="/admin/login" method="post">
                    <p class="msg">${message}</p>
                    <div class="input_area">
                        <input type="text" id="user_id" placeholder="아이디" name="a_id"><br>
                        <input type="password" id="user_pwd" placeholder="비밀번호" name="a_pwd">
                    </div>
                    <div class="btn_area">
                        <button id="login_btn">로그인</button>
                    </div>
                </form>
            </div>
        </div>
    </main>
</body>
</html>