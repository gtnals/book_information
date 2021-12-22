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
        $(".popup .top_area h2").html("대출 정보 추가");
        $(".popup .top_area p").html("대출 정보를 입력해주세요.");
    })

    $("#add_bb").click(function(){
        if(confirm("대출 정보를 등록하시겠습니까?")==false) return;
        let mi_seq=$("#bb_mem_id").attr("data-dep-seq")
        let bi_seq=$("#bb_book_num").attr("data-dep-seq")

        if(mi_seq==undefined){
            alert("회원정보를 입력해주세요");
            return;
        }
        if(bi_seq==undefined){
            alert("도서정보를 입력해주세요");
            return;
        }

        let data = {
            bbi_mi_seq: mi_seq,
            bbi_bi_seq: bi_seq
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

        $("#bb_mem_id").attr("data-dep-seq", "");
        $("#bb_mem_id").attr("m_st", "");
        $("#bb_mem_id").val("");
        $("#bb_book_num").attr("data-dep-seq", "");
        $("#bb_book_num").attr("b_st", "");
        $("#bb_book_num").val("");

        $(".popup_wrap").removeClass("open");
    })

    $(".delete_btn").click(function(){
        if(!confirm("반납하시겠습니까?")) return
        let seq = $(this).attr("data-seq");
        let bi_seq = $(this).attr("bi_seq");
        let mi_seq = $(this).attr("mi_seq")
        let days = $(this).attr("days")
        
        $.ajax({
            url:"/borrow/delete?seq="+seq+"&bi_seq="+bi_seq+"&mi_seq="+mi_seq+"&days="+days,
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

    $("#search_mem").click(function(){
        $(".department_search").css("display", "block");
        $("#book_search_btn").css("display", "none");
        $("#mem_search_btn").css("display", "block");
        $("#book_keyword").css("display", "none");
        $("#mem_keyword").css("display", "block");
    });
    $("#search_bo").click(function(){
        $(".department_search").css("display", "block");
        $("#book_search_btn").css("display", "block");
        $("#mem_search_btn").css("display", "none");
        $("#book_keyword").css("display", "block");
        $("#mem_keyword").css("display", "none");
    });
    $("#dep_search_close").click(function(){
        $(".result ul").html("");
        $(".department_search").css("display", "");
    });
    $("#mem_keyword").keyup(function(e){
        if(e.keyCode==13) $("#mem_search_btn").trigger("click");
    })
    $("#book_keyword").keyup(function(e){
        if(e.keyCode==13) $("#book_search_btn").trigger("click");
    })
    $("#mem_search_btn").click(function(){
        $.ajax({
            url:"/member/keyword?keyword="+$("#mem_keyword").val(),
            type:"get",
            success:function(r){
                console.log(r);
                $(".result ul").html("");
                for(let i=0; i<r.list.length; i++){
                    let str_status="";
                    if(r.list[i].mi_status==0) str_status="정상"
                    if(r.list[i].mi_status==1) str_status="경고"
                    if(r.list[i].mi_status==2) str_status="정지"

                    let tag = 
                    '<li>'+
                        '<a href="#" data-dep-seq="'+r.list[i].mi_seq+'" m_st="'+r.list[i].mi_status+'">'
                            +r.list[i].mi_name + " ("+ r.list[i].mi_id+")"+'</a>'+
                        '<span class="status'+r.list[i].mi_status+'">'+str_status+'</span>'+
                    '</li>';
                    $(".result ul").append(tag);
                }

                $(".result ul a").click(function(e){
                    e.preventDefault(); //a태그의 링크 기능 제거
                    if($(this).attr("m_st")==2){
                        alert("대출이 정지된 회원입니다.")
                        return;
                    }
                    let seq = $(this).attr("data-dep-seq");
                    let name = $(this).html();

                    $("#bb_mem_id").attr("data-dep-seq", seq);
                    $("#bb_mem_id").val(name);

                    $(".result ul").html("");
                    // $("#mem_keyword").val("");
                    $(".department_search").css("display", "");
                })
            }
        })
    })
    $("#book_search_btn").click(function(){
        $.ajax({
            url:"/book/keyword?keyword="+$("#book_keyword").val(),
            type:"get",
            success:function(r){
                console.log(r);
                $(".result ul").html("");
                for(let i=0; i<r.list.length; i++){
                    let str_status="";
                    if(r.list[i].bi_status==0) str_status="대출가능"
                    if(r.list[i].bi_status==1) str_status="대출중"
                    if(r.list[i].bi_status==2) str_status="연체중"
                    if(r.list[i].bi_status==3) str_status="예약중"
                    if(r.list[i].bi_status==4) str_status="이용불가"

                    let tag = 
                    '<li>'+
                        '<a href="#" data-dep-seq="'+r.list[i].bi_seq+'" b_st="'+r.list[i].bi_status+'">'+
                            r.list[i].bi_name + " ("+ r.list[i].bi_number+")"+'</a>'+
                        '<span class="status'+r.list[i].bi_status+'">'+str_status+'</span>'+
                    '</li>';
                    $(".result ul").append(tag);
                }

                $(".result ul a").click(function(e){
                    e.preventDefault(); //a태그의 링크 기능 제거
                    if($(this).attr("b_st")!=0){
                        alert("대출 불가 도서입니다.")
                        return;
                    }

                    let seq = $(this).attr("data-dep-seq");
                    let name = $(this).html();

                    $("#bb_book_num").attr("data-dep-seq", seq);
                    $("#bb_book_num").val(name);

                    $(".result ul").html("");
                    // $("#mem_keyword").val("");
                    $(".department_search").css("display", "");
                })
            }
        })
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