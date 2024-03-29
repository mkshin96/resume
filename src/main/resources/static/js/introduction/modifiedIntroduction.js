$(".modified_introduction").click(function () {
    var modified_idx = $(this).val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var jsonData = JSON.stringify({
        title : $("#title").text(),
        growth : $("#growth").text(),
        reason : $("#reason").text(),
        strength : $("#strength").text(),
        weakness : $("#weakness").text(),
        aspiration : $("#aspiration").text()
    });

    $.ajax({
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token);
        },
        url: "/introduction/" + modified_idx,
        type: "PUT",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.href = '/introduction';
        },
        error: function () {
            alert("수정 실패");
        }
    });
});

$(".enable_modified").click(function () {
    if (document.getElementById("title").isContentEditable == false) {
        document.getElementById("title").setAttribute("contenteditable", "true");
        document.getElementById("growth").setAttribute("contenteditable", "true");
        document.getElementById("reason").setAttribute("contenteditable", "true");
        document.getElementById("strength").setAttribute("contenteditable", "true");
        document.getElementById("weakness").setAttribute("contenteditable", "true");
        document.getElementById("aspiration").setAttribute("contenteditable", "true");

    }
    else {
        document.getElementById("title").setAttribute("contenteditable", "false");
        document.getElementById("growth").setAttribute("contenteditable", "false");
        document.getElementById("reason").setAttribute("contenteditable", "false");
        document.getElementById("strength").setAttribute("contenteditable", "false");
        document.getElementById("weakness").setAttribute("contenteditable", "false");
        document.getElementById("aspiration").setAttribute("contenteditable", "false");
    }
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
    $("#" + e +"Length").text($("#" + e).text().length);
}
