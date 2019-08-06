$(".introduction_register").click(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var jsonData = JSON.stringify({
        title : $("#title").val(),
        growth : $("#growth").val(),
        reason : $("#reason").val(),
        strength : $("#strength").val(),
        weakness : $("#weakness").val(),
        aspiration : $("#aspiration").val()
    });

    $.ajax({
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token);
        },
        url: "/introduction",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            alert("등록 성공");
            location.href = '/introduction';
        },
        error: function () {
            alert("등록 실패!");
        }
    });

});

//글자 수 카운트
$(function () {
    letterLength("growth");
    letterLength("reason");
    letterLength("strength");
    letterLength("weakness");
    letterLength("aspiration");
});

function letterLength(e) {
    $(document).on("keyup", "#" + e, function () {
        lengthHtml(e);
    })
}

function lengthHtml(e) {
    $("#" + e +"Length").text($("#" + e).val().length);
}
