$(".modified").click(function () {
    var modified_idx = $(this).val();
    location.href = "/projects/modified/" + modified_idx;
});

$(".delete").click(function () {
    var token = $("meta[name = '_csrf']").attr("content");
    var header = $("meta[name = '_csrf_header']").attr("content");

    var delete_idx = $(this).val();

    if(confirm("정말 삭제하시겠습니까?") == false) return;

    $.ajax({
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token)
        },
        url: "/projects/" + delete_idx,
        type: "DELETE",
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.href = '/projects';
        },
        error: function () {
        }
    });
});