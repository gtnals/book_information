// book.js
$(function(){
    $(".main_menu a:nth-child(2)").addClass("active");

    $("#detail_btn").click(function(){
        $(".detail_search_box").css("display", "block")
        $("#detail_btn").html("닫기")
        $("#detail_btn").attr("id","close_btn")

        $("#close_btn").click(function(){
            $(".detail_search_box").css("display", "none")
            $("#close_btn").html("상세 검색")
            $("#close_btn").attr("id","detail_btn")
        })
    })
    $("#reset_btn").click(function(){
        $("#key_b_name").val("");
        $("#key_b_author").val("");
        $("#key_b_publisher").val("");
        $("#key_b_category").val("0").prop("selected", true);
    })

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
        let b_publisher = $("#b_publisher").val()
        let b_category = $("#b_category option:selected").val()
        let b_publication_date= $("#b_publication_date").val()
        let b_page= $("#b_page").val()
        let b_image= $("#b_image").val()

        let data = {
            bi_name:b_name,
            bi_number:b_number,
            bi_status:b_status,
            bi_ai_seq:b_author,
            bi_publisher:b_publisher,
            bi_category:b_category,
            bi_publication_date:b_publication_date,
            bi_page:b_page,
            bi_image:b_image
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
        $("#b_author").append('<option value="0">저자명 입력</option>')
        $("#author_name").val("");
        $("#b_publisher").val("");
        $("#b_category").val("0").prop("selected", true);
        $("#b_publication_date").val("");
        $("#b_page").val("");
        $("#b_image").val("");

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
                $("#b_publisher").val(r.data.bi_publisher)
                $("#b_category").val(r.data.bi_category).prop("selected", true);
                $("#b_publication_date").val(r.data.bi_publication_date)
                $("#b_page").val(r.data.bi_page)
                $("#b_image").val(r.data.bi_image)
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
        let b_publisher = $("#b_publisher").val()
        let b_category = $("#b_category option:selected").val()
        let b_publication_date= $("#b_publication_date").val()
        let b_page= $("#b_page").val()
        let b_image= $("#b_image").val()

        let data = {
            bi_seq:modify_data_seq,
            bi_name:b_name,
            bi_number:b_number,
            bi_status:b_status,
            bi_ai_seq:b_author,
            bi_publisher:b_publisher,
            bi_category:b_category,
            bi_publication_date:b_publication_date,
            bi_page:b_page,
            bi_image:b_image
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
        let opt = $("#search_opt").val();
        location.href="/book?keyword="+$("#keyword").val()+"&key_opt="+opt;
    })
    $("#detail_search_btn").click(function(){
        let name = $("#key_b_name").val();
        let author = $("#key_b_author").val();
        let publisher = $("#key_b_publisher").val();
        let category = $("#key_b_category").val();
        location.href = "/book?key_opt=3&name="+name+"&author="+author+"&publisher="+publisher+"&category="+category;
    })
    $("#keyword").keydown(function(e){
        //console.log(e.keyCode)
        if(e.keyCode==13){
            $("#search_btn").trigger("click");
        }
    })

    $("#key_b_name").keydown(function(e){
        //console.log(e.keyCode)
        if(e.keyCode==13){
            $("#detail_search_btn").trigger("click");
        }
    })
    $("#key_b_author").keydown(function(e){
        //console.log(e.keyCode)
        if(e.keyCode==13){
            $("#detail_search_btn").trigger("click");
        }
    })
    $("#key_b_publisher").keydown(function(e){
        //console.log(e.keyCode)
        if(e.keyCode==13){
            $("#detail_search_btn").trigger("click");
        }
    })

    $("#author_name").keydown(function(e){
        //console.log(e.keyCode)
        if(e.keyCode==13){
            $("#author_search_btn").trigger("click");
        }
    })
    
    
})