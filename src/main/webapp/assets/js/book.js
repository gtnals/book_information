// book.js
$(function(){
    $(".main_menu a:nth-child(2)").addClass("active");
    $("#add_book").click(function(){
        $(".popup_wrap").addClass("open");
        $("#mod_b").css("display","none");
        $("#add_b").css("display","inline-block");
        $(".popup .top_area h2").html("도서 추가");
        $(".popup .top_area p").html("도서 정보를 입력해주세요.");
    })
    $("#add_b").click(function(){
        if(confirm("도서를 등록하시겠습니까?")==false) return;
        let b_name = $("#b_name").val()
        let b_number = $("#b_number").val()
        let b_status = $("#b_status option:selected").val()
        let b_author = $("#b_author option:selected").val()

        let data = {
            bi_name:b_name,
            bi_number:b_number,
            bi_status:b_status,
            bi_ai_seq:b_author
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
        $("#b_author").empty()
        $("#author_name").val("");

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
                if(r.list.length==0){
                    $("#b_author").empty()
                    $("#b_author").append('<option value="0">해당 작가 없음</option>')
                }
                else{
                    $("#b_author").empty()
                    for(let i=0; i< r.list.length; i++){
                        $("#b_author").append('<option value='+r.list[i].ai_seq+'>'+r.list[i].ai_number+'</option>')
                    }
                }
                //alert(r.list[0].ai_number)
            }
        })
    })

    let modify_data_seq=0;

    $(".modify_btn").click(function(){
        //alert($(this).attr("data-seq"))
        modify_data_seq=$(this).attr("data-seq");
        $(".popup_wrap").addClass("open");
        $("#add_b").css("display","none");
        $("#mod_b").css("display","inline-block");
        $(".popup .top_area h2").html("도서 수정");
        $(".popup .top_area p").html("수정할 내용을 입력해주세요.");
        $.ajax({
            type:"get",
            url:"/book/get?seq="+$(this).attr("data-seq"),
            success:function(r) {
                console.log(r);
                $("#b_name").val(r.data.bi_name)
                $("#b_number").val(r.data.bi_number)
                $("#b_status").val(r.data.bi_status).prop("selected", true);
                $("#author_name").val(r.data.author);
                $("#b_author").empty()
                $("#b_author").append('<option value='+r.data.bi_ai_seq+'>'+r.data.author_code+'</option>')
            }
        })
    })

    $("#mod_b").click(function(){
        //alert(modify_data_seq)
        if(confirm("수정하시겠습니까?")==false) return;

        let b_name = $("#b_name").val()
        let b_number = $("#b_number").val()
        let b_status = $("#b_status option:selected").val()
        let b_author = $("#b_author option:selected").val()

        let data = {
            bi_seq:modify_data_seq,
            bi_name:b_name,
            bi_number:b_number,
            bi_status:b_status,
            bi_ai_seq:b_author
        }

        $.ajax({
            type:"patch",
            url:"/book/update",
            data:JSON.stringify(data),
            contentType:"application/json",
            success:function(r){
                alert(r.message);
                location.reload();
            }
        })
    })

    $("#search_btn").click(function(){
        location.href="/book?keyword="+$("#keyword").val();
    })
    $("#keyword").keydown(function(e){
        console.log(e.keyCode)
        if(e.keyCode==13){
            $("#search_btn").trigger("click");
        }
    })
    $("#author_name").keydown(function(e){
        console.log(e.keyCode)
        if(e.keyCode==13){
            $("#author_search_btn").trigger("click");
        }
    })
})