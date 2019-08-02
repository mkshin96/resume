$(".introduction_register").click(function () {

    var jsonData = JSON.stringify({
        title : $("#title").val(),
        growth : $("#growth").val(),
        reason : $("#reason").val(),
        strength : $("#strength").val(),
        weakness : $("#weakness").val(),
        aspiration : $("#aspiration").val()
    });

    $.ajax({
        url: "/introduction",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.href = '/introduction';
        },
        error: function () {
            alert("등록 실패!");
        }
    });

});