$("#register").click(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

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
            alert("등록 성공");
            location.href = '/login';
        },
        error: function () {
            alert("등록 실패!");
        }
    });

   // $(function () {
   //     $(document).ajaxSettings.setRequestHeader(header, token);
   //     $.ajax({
   //         url: "/userRegister",
   //         type: "POST",
   //         data: jsonData,
   //         contentType: "application/json",
   //         dataType: "json",
   //         success: function () {
   //             alert("등록 성공");
   //             location.href = '/login';
   //         },
   //         error: function () {
   //             alert("등록 실패!");
   //         }
   //     });
   // });


});