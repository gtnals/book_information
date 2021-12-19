// book_detail.js
$(function(){
    $('#list_btn').click(function(){
        history.back();
    })
    
    $('#review_btn').click(function(){
        alert("리뷰 작성 버튼 클릭")
        // 리뷰 테이블 (중복 작성 가능)
    })
    $('#reserve_btn').click(function(){
        alert("예약버튼 클릭")
        // 클릭 시 회원 번호, 해당 도서 번호, 순번 -> 예약 정보 테이블 (예약(대기) 2명까지 둠)
        // due date까지 대출 안하면 다음 턴
    })
    $('#like_btn').click(function(){
        alert("추천버튼 클릭")
        // 클릭 당시 회원 번호, 해당 도서 번호 저장 -> 추천 정보 테이블 ***(중복 추천 불가능하게)
    })
})