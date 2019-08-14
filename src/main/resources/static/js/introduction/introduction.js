$(".modified").click(function () {
    var modified_idx = $(this).val();
    location.href = "/introduction/" + modified_idx;
});

$(".delete").click(function () {
    var delete_idx = $(this).val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if(confirm("정말 삭제하시겠습니까?") == false) return;

    $.ajax({
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token);
        },
        url: "/introduction/" + delete_idx,
        type: "DELETE",
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.href = '/introduction';
        },
        error: function () {
        }
    });
});