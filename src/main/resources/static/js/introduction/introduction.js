$(".modified").click(function () {
    var modified_idx = $(this).val();
    location.href = "/introduction/modified/" + modified_idx;
});

$(".delete").click(function () {
    var delete_idx = $(this).val();

    if(confirm("정말 삭제하시겠습니까?") == false) return;

    $.ajax({
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