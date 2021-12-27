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
    <link rel="stylesheet" href="/assets/css/board_list.css">
    <script src="/assets/js/board.js"></script>
</head>
<body>
    <main>
        <h1><i class="fas fa-list-ul"></i> 게시판 관리</h1>
        <div class="content_area">
            <div class="menu_area">
                <div class="search_box">
                    <select id="search_opt">
                        <option value="0" <c:if test="${data.key_opt==0}">selected</c:if> >전체</option>
                        <option value="1" <c:if test="${data.key_opt==1}">selected</c:if> >제목</option>
                        <option value="2" <c:if test="${data.key_opt==2}">selected</c:if> >내용</option>
                        <option value="3" <c:if test="${data.key_opt==3}">selected</c:if> >이름(ID)</option>
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
                <select id="order">
                    <option value="0" <c:if test="${data.order=='0'}">selected</c:if> >최신순</option>
                    <option value="1" <c:if test="${data.order=='1'}">selected</c:if> >상태순</option>
                    <option value="2" <c:if test="${data.order=='2'}">selected</c:if> >ID순</option>
                    <option value="3" <c:if test="${data.order=='3'}">selected</c:if> >카테고리순</option>
                </select>
            </div>
            <div class="table_area">
                <table>
                    <thead>
                        <tr>
                            <th class="small">번호</th>
                            <th class="small">카테고리</th>
                            <th class="big">이름(ID)</th>
                            <th class="big">제목</th>
                            <th class="small">상태</th>
                            <th class="big">등록일</th>
                            <th class="big">수정일</th>
                            <th class="big">조작</th>
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
                                <td>${d.mbi_seq}</td>
                                <td class="book_status">
                                    <c:if test="${d.mbi_category==0}">
                                        <span style="background-color: rgb(110, 131, 248);">도서신청</span>
                                    </c:if>
                                    <c:if test="${d.mbi_category==1}">
                                        <span style="background-color: rgb(240, 108, 158);">훼손신고</span>
                                    </c:if>
                                    <c:if test="${d.mbi_category==2}">
                                        <span style="background-color: rgb(219, 132, 236);">기타</span>
                                    </c:if>
                                </td>
                                <td>${d.mem_name} (${d.mem_id})</td>
                                <td><button class="author_detail_btn" data-seq="${d.mbi_seq}">${d.mbi_title}</button></td>
                                <td class="book_status">
                                    <c:if test="${d.mbi_status==0}">
                                        <span style="background-color: rgb(251, 186, 64);">대기</span>
                                    </c:if>
                                    <c:if test="${d.mbi_status==1}">
                                        <span style="background-color: rgb(17,226,27);">완료</span>
                                    </c:if>
                                    <c:if test="${d.mbi_status==2}">
                                        <span style="background-color: rgb(255, 23, 23);">답변</span>
                                    </c:if>
                                </td>
                                <td><fmt:formatDate value="${d.mbi_mod_dt}" pattern="yyyy-MM-dd (EE) HH:mm:ss"/></td>
                                <!-- 오전/오후: a hh:~ -->
                                <td><fmt:formatDate value="${d.mbi_reg_dt}" pattern="yyyy-MM-dd (EE) HH:mm:ss"/></td>
                                <td>
                                    <button class="modify_btn" data-seq="${d.mbi_seq}"><i class="fas fa-comment-dots"></i></button>
                                    <button class="delete_btn" data-seq="${d.mbi_seq}"><i class="fas fa-minus-circle"></i></button>
                                </td>
                            </tr>
                            <tr class="content_row">
                                <td class="content_head" colspan="2">닫기버튼</td>
                                <td class="content" colspan="5"><div>${d.mbi_content}</div></td>
                                <td></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="pager_area">
                <button id="prev"><i class="fas fa-chevron-left"></i></button>
                <div class="pagers">
                    <c:forEach begin="1" end="${data.pageCnt}" var="i">
                        <a href="/board?offset=${(i-1)*10}&keyword=${data.keyword}&order=${data.order}">${i}</a>
                    </c:forEach>
                </div>
                <button id="next"><i class="fas fa-chevron-right"></i></button>
            </div>
        </div>
    </main>
</body>
</html>