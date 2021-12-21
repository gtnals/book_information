<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
    <script src="/assets/js/member.js"></script>
</head>
<body>
    <main>
        <h1><i class="fas fa-book-reader"></i> 회원 관리</h1>
        <button id="add_book"><i class="fas fa-plus-circle"></i> 회원 추가</button>
        <div class="content_area">
            <div class="menu_area">
                <div class="search_box">
                    <select id="search_opt">
                        <option value="0" <c:if test="${data.key_opt==0}">selected</c:if> >전체</option>
                        <option value="1" <c:if test="${data.key_opt==1}">selected</c:if> >ID</option>
                        <option value="2" <c:if test="${data.key_opt==2}">selected</c:if> >이름</option>
                        <option value="3" <c:if test="${data.key_opt==3}">selected</c:if> >이메일</option>
                    </select>
                    <input type="text" id="keyword" placeholder="검색어 입력" value="${data.keyword}">
                    <button id="search_btn"><i class="fas fa-search"></i></button>
                </div>
                <div class="search_result" 
                    <c:if test="${data.keyword!=''}">
                        <p>*총 ${data.total}명이 검색되었습니다.</p>
                    </c:if>
                </div>
            </div>
            <div class="order_select">
                <select id="order">
                    <option value="0" <c:if test="${data.order=='0'}">selected</c:if> >최신순</option>
                    <option value="1" <c:if test="${data.order=='1'}">selected</c:if> >상태순</option>
                    <option value="2" <c:if test="${data.order=='2'}">selected</c:if> >ID순</option>
                    <option value="3" <c:if test="${data.order=='3'}">selected</c:if> >이름순</option>
                </select>
            </div>
            <div class="table_area">
                <table>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>ID</th>
                            <th>이름</th>
                            <th>생년월일</th>
                            <th>전화번호</th>
                            <th>이메일</th>
                            <th>상태</th>
                            <th>등록일</th>
                            <th>수정일</th>
                            <th>조작</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${data.total==0}">
                            <tr>
                                <td id="nodata" colspan="11">데이터가 없습니다.</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${data.list}" var="d">
                            <tr>
                                <td>${d.mi_seq}</td>
                                <td>${d.mi_id}</td>
                                <td>${d.mi_name}</td>
                                <td>${d.mi_birth}</td>
                                <td>${d.mi_phone}</td>
                                <td>${d.mi_email}</td>
                                <td class="book_status">
                                    <c:if test="${d.mi_status==0}">
                                        <span style="background-color: rgb(17,226,27);">정상</span>
                                    </c:if>
                                    <c:if test="${d.mi_status==1}">
                                        <span style="background-color: rgb(251, 186, 64);">경고</span>
                                    </c:if>
                                    <c:if test="${d.mi_status==2}">
                                        <span style="background-color: rgb(255, 23, 23);">정지</span>
                                    </c:if>
                                </td>
                                <td><fmt:formatDate value="${d.mi_reg_dt}" pattern="yyyy-MM-dd (EE) HH:mm:ss"/></td>
                                <td><fmt:formatDate value="${d.mi_mod_dt}" pattern="yyyy-MM-dd (EE) HH:mm:ss"/></td>
                                <td>
                                    <button class="modify_btn" data-seq="${d.mi_seq}"><i class="fas fa-pencil-alt"></i></button>
                                    <button class="delete_btn" data-seq="${d.mi_seq}"><i class="fas fa-minus-circle"></i></button>
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
                        <a href="/member?offset=${(i-1)*10}&keyword=${data.keyword}&order=${data.order}">${i}</a>
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
                    <i class="fas fa-user"></i>
                </div>
                <h2>회원 추가</h2>
                <p>회원 정보를 입력해주세요</p>
            </div>
            <div class="content_area">
                <input type="text" id="m_name" placeholder="이름">
                <input type="text" id="m_id" placeholder="아이디">
                <input type="password" id="m_pwd" placeholder="비밀번호">
                <input type="password" id="m_pwd_confirm" placeholder="비밀번호 확인">
                <input type="text" id="m_birth" placeholder="생년월일 (YYYYMMDD)">
                <input type="text" id="m_phone" placeholder="전화번호 (01012345678)">
                <input type="text" id="m_email" placeholder="이메일 (mail@mail.com)">
                <select id="m_status">
                    <option value="0">정상</option>
                    <option value="1">경고</option>
                    <option value="2">정지</option>
                </select>
                <select id="m_role">
                    <option value="0">일반</option>
                    <option value="99">관리자</option>
                </select>
            </div>
            <div class="btn_area">
                <button id="add_m">등록하기</button>
                <button id="mod_m">수정하기</button>
                <button id="cancel_m">취소하기</button>
            </div>
        </div>
    </div>
</body>
</html>