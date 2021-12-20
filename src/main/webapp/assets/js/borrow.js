// borrow.js
$(function(){
    $(".main_menu a:nth-child(3)").addClass("active");

    $("#detail_btn").click(function(){
        $(this).attr("overdue_check", "1")
    })

    $("#add_book").click(function(){
        $(".popup_wrap").addClass("open");
        $("#mod_bb").css("display","none");
        $("#add_bb").css("display","inline-block");
        $(".popup .top_area h2").html("대출");
        $(".popup .top_area p").html("대출 정보를 입력해주세요.");
    })

    $("#add_bb").click(function(){
        if(confirm("대출 정보를 등록하시겠습니까?")==false) return;

        let data = {
            mem_id:$("#bb_mem_id").val(),
            book_num:$("#bb_book_num").val(),
            // bbi_borrow_date:$("#bb_borrow_date").val(),
            // bbi_due_date:$("#bb_due_date").val()
        }

        $.ajax({
            url:"/borrow/add",
            type:"post",
            contentType:"application/json",
            data:JSON.stringify(data),
            success:function(r){
                alert(r.message)
                if(r.status)
                    location.reload()
            }
        })
    })
    $("#cancel_bb").click(function(){
        if(confirm("취소하시겠습니까?\n(입력된 정보는 저장되지 않습니다.)")==false) return;

        $("#bb_mem_id").val("")
        $("#bb_book_num").val("")

        $(".popup_wrap").removeClass("open");
    })
})

function setDate(){
    alert("날짜 선택")
}