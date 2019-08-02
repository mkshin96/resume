$(".project_register").click(function () {

    var jsonData = JSON.stringify({
        name : $("#name").val(),
        period : $("#period").val(),
        description : $("#description").val(),
        persons : $("#persons").val()
    });

    $.ajax({
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