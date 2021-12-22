// book.js
$(function(){
    $(".main_menu a:nth-child(2)").addClass("active");

    $("#detail_btn").click(function(){
        $(".detail_search_box").css("display", "block")
    })
    $("#detail_close_btn").click(function(){
        $(".detail_search_box").css("display", "none")
    })
    $("#reset_btn").click(function(){
        $("#key_b_name").val("");
        $("#key_b_author").val("");
        $("#key_b_publisher").val("");
        $("#key_b_category").val("-1").prop("selected", true);
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
        let b_translator = $("#b_translator").val()
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
            bi_image:b_image,
            bi_translator:b_translator
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
        $("#b_status").val("0").prop("selected", true).attr("disabled", false);
        $("#b_author").empty()
        $("#b_author").append('<option value="0">저자코드</option>')
        $("#author_name").val("");
        $("#b_translator").val("");
        $("#b_publisher").val("");
        $("#b_category").val("0").prop("selected", true);
        $("#b_publication_date").val("");
        $("#b_page").val("");
        $("#b_image").val("");

        $(".popup_wrap").removeClass("open");
    });

    $(".delete_btn").click(function(){
        let book_status = $(this).attr("book-status")
        if(book_status==1||book_status==2){
            alert("삭제가 불가능한 도서입니다. (대출 중)")
            return;
        }
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
                    $("#b_author").empty();
                    $("#b_author").append('<option value="0">해당 작가 없음</option>');
                }
                else{
                    $("#b_author").empty();
                    for(let i=0; i< r.list.length; i++)
                    $("#b_author").append('<option value='+r.list[i].ai_seq+'>'+r.list[i].ai_number+'</option>')
                }
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
                if(r.data.bi_status==0 ||r.data.bi_status==4)
                    $("#b_status").val(r.data.bi_status).prop("selected", true);
                else
                    $("#b_status").val(r.data.bi_status).prop("selected", true).attr("disabled", true);
                $("#author_name").val(r.data.author);
                $("#b_author").empty()
                $("#b_author").append('<option value='+r.data.bi_ai_seq+'>'+r.data.author_number+'</option>')
                $("#b_publisher").val(r.data.bi_publisher)
                $("#b_translator").val(r.data.bi_translator)
                $("#b_category").val(r.data.bi_category).prop("selected", true);
                $("#b_publication_date").val(r.data.bi_publication_date)
                $("#b_page").val(r.data.bi_page)
                $("#b_image").val(r.data.bi_image)
            }
        })
    })

    $("#mod_b").click(function(){
        if(confirm("수정하시겠습니까?")==false) return;

        let b_name = $("#b_name").val()
        let b_number = $("#b_number").val()
        let b_status = $("#b_status option:selected").val()
        let b_author = $("#b_author option:selected").val()
        let b_publisher = $("#b_publisher").val()
        let b_translator = $("#b_translator").val()
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
            bi_image:b_image,
            bi_translator:b_translator
        }

        $.ajax({
            type:"patch",
            url:"/book/update",
            data:JSON.stringify(data),
            contentType:"application/json",
            success:function(r){
                alert(r.message);
                if(r.status)
                    location.reload();
            }
        })
    })

    $("#search_btn").click(function(){
        let opt = $("#search_opt").val();
        let order = $("#order option:selected").val();
        location.href="/book?keyword="+$("#keyword").val()+"&key_opt="+opt+"&order="+order;
    })
    $("#detail_search_btn").click(function(){
        let name = $("#key_b_name").val();
        let author = $("#key_b_author").val();
        let publisher = $("#key_b_publisher").val();
        let category = $("#key_b_category").val();
        let order = $("#order option:selected").val();
        location.href = "/book?key_opt=3&name="+name+"&author="+author+"&publisher="+publisher+"&category="+category+"&order="+order;
    })
    $("#keyword").keyup(function(e){
        //console.log(e.keyCode)
        if(e.keyCode==13){
            $("#search_btn").trigger("click");
        }
    })

    $("#key_b_name").keyup(function(e){
        //console.log(e.keyCode)
        if(e.keyCode==13){
            $("#detail_search_btn").trigger("click");
        }
    })
    $("#key_b_author").keyup(function(e){
        //console.log(e.keyCode)
        if(e.keyCode==13){
            $("#detail_search_btn").trigger("click");
        }
    })
    $("#key_b_publisher").keyup(function(e){
        //console.log(e.keyCode)
        if(e.keyCode==13){
            $("#detail_search_btn").trigger("click");
        }
    })

    $("#author_name").keyup(function(e){
        //console.log(e.keyCode)
        if(e.keyCode==13){
            $("#author_search_btn").trigger("click");
        }
    })
    
    $("#add_author").click(function(){
        $(".add_author").css("display","block")
        $("#mod_a").css("display","none");
        $("#add_a").css("display","inline-block");
        $(".add_author .top_area p").html('<i class="fas fa-feather"></i> 저자 추가');
    })

    $("#add_a").click(function(){
        if(confirm("작가를 등록하시겠습니까?")==false) return;
        let a_name = $("#a_name").val()
        let a_number = $("#a_number").val()
        let a_phone = $("#a_phone").val()
        let a_email = $("#a_email").val()
        let a_insta = $("#a_insta").val()       

        if(a_name==null || a_name=="") alert("저자명을 입력하세요.");
        if(a_number==null || a_number=="") alert("저자코드를 입력하세요.");

        let data = {
            ai_name:a_name,
            ai_number:a_number,
            ai_phone:a_phone,
            ai_email:a_email,
            ai_insta:a_insta,
        }

        $.ajax({
            type: "post",
            url: "/author/add",
            data:JSON.stringify(data),
            contentType: "application/json",
            success: function(r){
                alert(r.message);
                if(r.status)
                    $(".add_author").css("display","")
            }
        })
    })
    $("#cancel_a").click(function(){
        if(confirm("취소하시겠습니까?\n(입력된 정보는 저장되지 않습니다.)")==false) return;

        $("#a_name").val("")
        $("#a_number").val("")
        $("#a_phone").val("")
        $("#a_email").val("")
        $("#a_insta").val("")

        $(".add_author").css("display","")
    })

    let author_mod_seq = 0

    $(".author_detail_btn").click(function(){
        author_mod_seq = $(this).attr("data-seq")
        
        $(".add_author").css("display","block")
        $("#add_a").css("display","none");
        $("#mod_a").css("display","inline-block");
        $(".add_author .top_area p").html('<i class="fas fa-feather"></i> 저자 수정');

        $.ajax({
            type:"get",
            url:"/author/get?seq="+author_mod_seq,
            success:function(r){
                console.log(r)
                $("#a_name").val(r.author.ai_name)
                $("#a_number").val(r.author.ai_number)
                $("#a_phone").val(r.author.ai_phone)
                $("#a_email").val(r.author.ai_email)
                $("#a_insta").val(r.author.ai_insta)
            }
        })

    })
    $("#mod_a").click(function(){
        if(confirm("수정하시겠습니까?")==false) return;
        let a_name = $("#a_name").val()
        let a_number = $("#a_number").val()
        let a_phone = $("#a_phone").val()
        let a_email = $("#a_email").val()
        let a_insta = $("#a_insta").val()       

        if(a_name==null || a_name=="") alert("저자명을 입력하세요.");
        if(a_number==null || a_number=="") alert("저자코드를 입력하세요.");

        let data = {
            ai_seq:author_mod_seq,
            ai_name:a_name,
            ai_number:a_number,
            ai_phone:a_phone,
            ai_email:a_email,
            ai_insta:a_insta,
        }

        $.ajax({
            type: "patch",
            url: "/author/update",
            data:JSON.stringify(data),
            contentType: "application/json",
            success: function(r){
                alert(r.message);
                if(r.status)
                    // $(".add_author").css("display","")
                    location.reload()
            }
        })
    })
})

function orderList(current_opt){
    if(current_opt==3) {
        $("#detail_search_btn").trigger("click");
    }
    else {
        $("#search_btn").trigger("click");
    }
}
