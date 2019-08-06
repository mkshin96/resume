$(".project_register").click(function () {
    var token = $("meta[name = '_csrf']").attr("content");
    var header = $("meta[name = '_csrf_header']").attr("content");

    var jsonData = JSON.stringify({
        name : $("#name").val(),
        period : $("#period").val(),
        description : $("#description").val(),
        persons : $("#persons").val()
    });

    $.ajax({
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token)
        },
        url: "/projects",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.href = '/projects';
        },
        error: function () {
        }
    });

});