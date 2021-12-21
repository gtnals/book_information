// borrow.js
$(function(){
    $(".main_menu a:nth-child(4)").addClass("active");

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

    $(".delete_btn").click(function(){
        if(!confirm("반납하시겠습니까?")) return
        let seq = $(this).attr("data-seq");
        let bi_seq = $(this).attr("bi_seq");
        
        $.ajax({
            url:"/borrow/delete?seq="+seq+"&bi_seq="+bi_seq,
            type:"delete",
            success:function(r){
                alert(r.message)
                if(r.status)
                    location.reload()
            }
        })
    })

    $(".modify_btn").click(function(){
        alert("연장 버튼 클릭")
        //if(!confirm("연장하시겠습니까?")) return;

        //대출기한 연장 처리 -반납일 알림 띄움
    })

    $("#search_btn").click(function(){
        let opt = $("#search_opt").val();
        let order = $("#order option:selected").val();
        location.href="/borrow?keyword="+$("#keyword").val()+"&key_opt="+opt+"&order="+order;
    })
    $("#keyword").keyup(function(e){
        //console.log(e.keyCode)
        if(e.keyCode==13){
            $("#search_btn").trigger("click");
        }
    })
})

function orderList(){
    $("#search_btn").trigger("click");
}

// function setDate(){
//     // let dateString = $("#bb_borrow_date").val()
//     // let year=dateString.substr(0,4)
//     // let month=dateString.substr(5,2)
//     // let date=dateString.substr(8,2)
//     // let due_date=new Date(year, month-1, date)
//     // due_date.setDate(due_date.getDate()+14)
// }