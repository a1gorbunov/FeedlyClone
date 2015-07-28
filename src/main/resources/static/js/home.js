function openFullMode(articleId) {
    $.ajax({
        url : "/description/"+articleId,
        success : function(data) {
            $('#contentRoot').html(data);
            $('#floating-entry').show();
        }
    });
}
function closeFloatingTab(){
    $('#contentRoot').empty();
    $('#floating-entry').hide();
}