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

    let answer_seq=0;
    $(".answer_btn").click(function(){
        answer_seq=$(this).attr("data-seq");
        $(".popup_wrap").addClass("open");
        $("#mod_bb").css("display","none");
        $("#add_bb").css("display","inline-block");
    })
    $("#cancel_bb").click(function(){
        if(confirm("취소하시겠습니까?\n(입력된 정보는 저장되지 않습니다.)")==false) return;
        $("#admin_answer").val("")
        $(".popup_wrap").removeClass("open");
    })
    $("#add_bb").click(function(){
        if(confirm("등록하시겠습니까?\n(*등록 후에는 답변 수정과 삭제가 불가능합니다.)")==false) return;
        content = $("#admin_answer").val().replace(/\n/g, '<br/>');
        let data = {
            mbi_seq: answer_seq,
            mbi_reply: content,
        }
        $.ajax({
            url:"/board/reply",
            type:"post",
            data: JSON.stringify(data),
            contentType:"application/json",
            success: function(r){
                alert(r.message);
                if(r.status) location.reload();
            }
        })
    })
})