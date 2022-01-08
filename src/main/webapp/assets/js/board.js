// board.js
$(function(){
    $(".main_menu a:nth-child(6)").addClass("active");

    $("#search_btn").click(function(){
        let opt = $("#search_opt").val();
        let order = $("#order option:selected").val();
        location.href="/board?keyword="+$("#keyword").val()+"&key_opt="+opt+"&order="+order;
    })
    $("#keyword").keyup(function(e){
        if(e.keyCode==13){
            $("#search_btn").trigger("click");
        }
    })

    $(".delete_btn").click(function(){
        if(confirm("게시글을 삭제하시겠습니까?\n(이 동작은 되돌릴 수 없습니다.)")==false) return;
        let seq = $(this).attr("data-seq");
        
        $.ajax({
            type:"delete",
            url:"/board/delete?seq="+seq,
            success:function(r){
                alert(r.message);
                location.reload();
            }
        })
    });
    
    $("#order").change(function(){
        $("#search_btn").trigger("click");
    })

    $(".author_detail_btn").click(function(){
        let seq = "content"+$(this).attr("data-seq");
        $('#'+seq).css("display","table-row");
        
    })
    $(".detail_close_btn").click(function(){
        let seq = "content"+$(this).attr("data-seq");
        $('#'+seq).css("display","none");
    })
    $(".ans_content_btn").click(function(){
        let seq = "answer"+$(this).attr("data-seq");
        $('#'+seq).css("display","table-row");
        
    })
    $(".answer_close_btn").click(function(){
        let seq = "answer"+$(this).attr("data-seq");
        $('#'+seq).css("display","none");
    })

    $(".answer_btn").click(function(){
        alert("답변 클릭")
        //답변 작성 팝업 띄우기
        //ㄴ> 답변 등록. 만 가능하게
    })
})