// borrow.js
$(function(){
    $(".main_menu a:nth-child(3)").addClass("active");

    $("#detail_btn").click(function(){
        $(this).attr("overdue_check", "1")
    })

    $("#add_book").click(function(){
        $(".popup_wrap").addClass("open");
        $("#mod_b").css("display","none");
        $("#add_b").css("display","inline-block");
        $(".popup .top_area h2").html("대출");
        $(".popup .top_area p").html("대출 정보를 입력해주세요.");
    })
    $("#add_bb").click(function(){
        if(confirm("대출 정보를 등록하시겠습니까?")==false) return;

        let data = {
            bbi_mem_id:$("#bb_mem_id").val(),
            bbi_book_num:$("#bb_book_num").val(),
            bbi_borrow_date:$("#bb_borrow_date").val(),
            bbi_due_date:$("#bb_due_date").val()
        }

    })
})