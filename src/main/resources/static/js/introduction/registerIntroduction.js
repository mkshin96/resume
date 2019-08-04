$(".introduction_register").click(function () {
    var jsonData = JSON.stringify({
        title : $("#title").val(),
        growth : $("#growth").val(),
        reason : $("#reason").val(),
        strength : $("#strength").val(),
        weakness : $("#weakness").val(),
        aspiration : $("#aspiration").val(),
        growthLength : $("#growthLength").text(),
        reasonLength : $("#reasonLength").text(),
        strengthLength : $("#strengthLength").text(),
        weaknessLength : $("#weaknessLength").text(),
        aspirationLength : $("#aspirationLength").text()
    });

    $.ajax({
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
letterLength("growth");
letterLength("reason");
letterLength("strength");
letterLength("weakness");
letterLength("aspiration");

function letterLength(e) {
    $(document).on("keyup", "#" + e, function () {
        lengthHtml(e);
    })
}

function lengthHtml(e) {
    $("#" + e +"Length").text($("#" + e).val().length);
}
