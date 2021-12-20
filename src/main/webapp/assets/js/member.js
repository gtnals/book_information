// member.js
$(function(){
    // $(".main_menu a:nth-child(2)").removeClass("active");
    $(".main_menu a:nth-child(4)").addClass("active");

    $("#add_book").click(function(){
        $(".popup_wrap").css("display", "block");
        $("#mod_m").css("display", "none");
        $("#add_m").css("display", "inline-block");
        $(".popup .top_area h2").html("회원 추가");
        $(".popup .top_area p").html("회원 정보를 입력해주세요");
    })
    $("#add_m").click(function(){
        let m_name = $("#m_name").val();
        let m_id = $("#m_id").val();
        let m_pwd = $("#m_pwd").val();
        let m_pwd_confirm = $("#m_pwd_confirm").val();
        let m_birth = $("#m_birth").val();
        let m_phone = $("#m_phone").val();
        let m_email = $("#m_email").val();
        let m_status = $("#m_status option:selected").val();
        
        if(m_name==''){
            alert("이름을 입력해주세요");
            return;
        }
        if(m_id==''){
            alert("아이디를 입력해주세요");
            return;
        }
        if(m_pwd==''){
            alert("비밀번호를 입력해주세요");
            return;
        }
        if(m_pwd != m_pwd_confirm) {
            alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.")
            return;
        }

        let data = {
            mi_name:m_name,
            mi_id:m_id,
            mi_pwd:m_pwd,
            mi_birth:m_birth,
            mi_phone:m_phone,
            mi_email:m_email,
            mi_status:m_status,
        }

        $.ajax({
            url:"/member/add",
            type:"post",
            data:JSON.stringify(data),
            contentType:"application/json",
            success: function(e){
                alert(e.message);
                if(e.status)
                    location.reload();
            }
        })
    })
    $("#cancel_m").click(function(){
        if(confirm("취소하시겠습니까?\n(입력된 내용은 저장되지 않습니다.)")==false) return;

        $("#m_name").val("");
        $("#m_id").val("");
        $("#m_pwd").val("");
        $("#m_pwd_confirm").val("");
        $("#m_birth").val("");
        $("#m_phone").val("");
        $("#m_email").val("");
        $("#m_status").val("0").prop("selected", true);

        $(".popup_wrap").css("display", "")
    })

    $("#search_btn").click(function(){
        let opt = $("#search_opt").val();
        let order = $("#order option:selected").val();
        location.href="/member?keyword="+$("#keyword").val()+"&key_opt="+opt+"&order="+order;
    })

    $("#keyword").keyup(function(e){
        if(e.keyCode==13){
            $("#search_btn").trigger("click");
        }
    })

    let mod_seq=0;

    $(".modify_btn").click(function(){
        mod_seq = $(this).attr("data-seq");

        $(".popup_wrap").css("display", "block");
        $("#add_m").css("display", "none");
        $("#mod_m").css("display", "inline-block");
        $(".popup .top_area h2").html("회원 수정");
        $(".popup .top_area p").html("수정할 회원 정보를 입력해주세요");

        $.ajax({
            url:"/member/get?seq="+mod_seq,
            type:"get",
            success:function(r){
                $("#m_name").val(r.data.mi_name);
                $("#m_id").val(r.data.mi_id);
                $("#m_pwd").val("*****");
                $("#m_pwd_confirm").val("*****");
                $("#m_pwd").attr("disabled", true)
                $("#m_pwd_confirm").attr("disabled", true)
                $("#m_birth").val(r.data.mi_birth);
                $("#m_phone").val(r.data.mi_phone);
                $("#m_email").val(r.data.mi_email);
                $("#m_status").val(r.data.mi_status).prop("selected", true);
            }
        })
    })

    $("#mod_m").click(function(){
        if(confirm("수정하시겠습니까?")==false) return;

        let data = {
            mi_seq:mod_seq,
            mi_name:$("#m_name").val(),
            mi_id:$("#m_id").val(),
            mi_birth:$("#m_birth").val(),
            mi_phone:$("#m_phone").val(),
            mi_email:$("#m_email").val(),
            mi_status:$("#m_status option:selected").val(),
        }

        $.ajax({
            type:"patch",
            url:"/member/update",
            data:JSON.stringify(data),
            contentType:"application/json",
            success:function(r){
                alert(r.message);
                if(r.status)
                    location.reload();
            }
        })
    })

    $(".delete_btn").click(function(){
        if(confirm("회원을 삭제하시겠습니까?\n(이 동작은 되돌릴 수 없습니다.)")==false) return;
        let seq = $(this).attr("data-seq");
        
        $.ajax({
            type:"delete",
            url:"/member/delete?seq="+seq,
            success:function(r){
                alert(r.message);
                location.reload();
            }
        })
    });
    
    $("#order").change(function(){
        $("#search_btn").trigger("click");
    })
})