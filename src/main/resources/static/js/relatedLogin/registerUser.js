$("#register").click(function () {
   var jsonData = JSON.stringify({
       id : $("#id").val(),
       password : $("#password").val(),
       email : $("#email").val()
   });

    $.ajax({
        url: "/userRegister",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            alert("등록 성공");
            location.href = '/login';
        },
        error: function () {
            alert("등록 실패!");
        }
    });

});