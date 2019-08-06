$(".modified_project").click(function () {
    var token = $("meta[name = '_csrf']").attr("content");
    var header = $("meta[name = '_csrf_header']").attr("content");

    var modified_idx = $(this).val();

    var jsonData = JSON.stringify({
        name : $(this).parent().parent().find("#name").text(),
        period : $(this).parent().parent().find("#period").text(),
        description : $(this).parent().parent().find("#description").text(),
        persons : $(this).parent().parent().find("#persons").text()
    });

    $.ajax({
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token)
        },
        url: "/projects/" + modified_idx,
        type: "PUT",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.href = '/projects';
        },
        error: function () {
            alert("수정 실패");
        }
    });
});

$(".enable_modified").click(function () {
    if (document.getElementById("name").isContentEditable == false) {
        document.getElementById("name").setAttribute("contenteditable", "true");
        document.getElementById("period").setAttribute("contenteditable", "true");
        document.getElementById("description").setAttribute("contenteditable", "true");
        document.getElementById("persons").setAttribute("contenteditable", "true");
    }
    else {
        document.getElementById("name").setAttribute("contenteditable", "false");
        document.getElementById("period").setAttribute("contenteditable", "false");
        document.getElementById("description").setAttribute("contenteditable", "false");
        document.getElementById("persons").setAttribute("contenteditable", "false");
    }
});
