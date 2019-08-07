var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var check = true;

$("#register").click(function () {
    if(check) {
        var jsonData = JSON.stringify({
            id: $("#id").val(),
            password: $("#password").val(),
            email: $("#email").val()
        });

        $.ajax({
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token)
            },
            url: "/userRegister",
            type: "POST",
            data: jsonData,
            contentType: "application/json",
            dataType: "json",
            success: function () {
                alert("등록 성공!");
                location.href = '/login';
            },
            error: function () {
                alert("등록 실패!");
            }
        });
    }
});

$("#id").blur(function () {
    if($("#id").val().trim().length == 0) {
        $("#idErrorMessage").html("필수 항목입니다.");
        check = false;
    }

    $.ajax({
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token)
        },
        url: "/userRegister/checkId",
        type: "POST",
        data: JSON.stringify({id : $("#id").val()}),
        contentType: "application/json",
        dataType: "json",
        success: function () {
        },
        error: function () {
            $("#idErrorMessage").html("중복된 아이디입니다.");
        }
    });
});

$("#email").blur(function () {

    //email 정규식
    var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

    if($("#email").val().trim().length == 0) {
        $("#emailErrorMessage").html("필수 항목입니다.");
        check = false;
    }

    if($("#email").val().match(regExp) == null) {
        $("#emailErrorMessage").html("이메일 형식과 맞지 않습니다.");
        check = false;
    }
    $.ajax({
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token)
        },
        url: "/userRegister/checkEmail",
        type: "POST",
        data: JSON.stringify({email : $("#email").val()}),
        contentType: "application/json",
        dataType: "json",
        success: function () {
        },
        error: function () {
            $("#email").html("중복된 이메일입니다.");
        }
    });
});