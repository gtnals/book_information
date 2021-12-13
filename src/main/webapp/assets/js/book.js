// book.js
$(function(){
    $(".main_menu a:nth-child(2)").addClass("active");
    $("#add_book").click(function(){
        $(".popup_wrap").addClass("open");
    })
    $("#add_b").click(function(){
        if(confirm("도서를 등록하시겠습니까?")==false) return;
        let b_name = $("#b_name").val()
        let b_number = $("#b_number").val()
        let b_status = $("#b_status option:selected").val()

        let data = {
            bi_name:b_name,
            bi_number:b_number,
            bi_status:b_status
        }

        $.ajax({
            type: "post",
            url: "/book/add",
            data:JSON.stringify(data),
            contentType: "application/json",
            success: function(r){
                alert(r.message);
                if(r.status)
                    location.reload();
            }
        })
    })
    $("#cancel_b").click(function(){
        if(confirm("취소하시겠습니까?\n(입력된 정보는 저장되지 않습니다.)")==false) return;

        $("#b_name").val("")
        $("#b_number").val("")
        $("#b_status").val("0").prop("selected", true);

        $(".popup_wrap").removeClass("open");
    });

    $(".delete_btn").click(function(){
        if(confirm("도서를 삭제하시겠습니까?\n(이 동작은 되돌릴 수 없습니다.)")==false) return;
        let seq = $(this).attr("data-seq");
        
        $.ajax({
            type:"delete",
            url:"/book/delete?seq="+seq,
            success:function(r){
                alert(r.message);
                location.reload();
            }
        })
    });

    $("#author_search_btn").click(function(){
        let author_name = $("#author_name").val()
        
        $.ajax({
            url:"/author/search?name="+author_name,
            type: "post",
            success: function(r){
                for(let i=0; i< r.list.length; i++){
                    //html append로 b_author 밑에 option 추가
                    //console.log(i)
                }
                //alert(r.list[0].ai_number)
            }
        })

    })
})