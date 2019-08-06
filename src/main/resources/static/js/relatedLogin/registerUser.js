var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$("#register").click(function () {
    var jsonData = JSON.stringify({
       id : $("#id").val(),
       password : $("#password").val(),
       email : $("#email").val()
   });

    $.ajax({
        beforeSend : function(xhr){
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
});

$("#id").blur(function () {
    var jsonData = JSON.stringify({
        id : $("#id").val()
    });
    $.ajax({
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token)
        },
        url: "/userRegister/checkId",
        type: "POST",
        data: jsonData,
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
    $.ajax({
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token)
        },
        url: "/userRegister/checkEmail",
        type: "POST",
        data: JSON.stringify($("#email").val()),
        contentType: "application/json",
        dataType: "json",
        success: function () {
        },
        error: function () {
            $("#email").html("중복된 이메일입니다.");
        }
    });
});