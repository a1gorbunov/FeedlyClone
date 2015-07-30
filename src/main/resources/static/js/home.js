function openFullMode(feedMessage) {
    $.ajax({
        type: 'POST',
        url : "/descriptionFull",
        data:"id="+feedMessage.id+"&categoryName="+feedMessage.title,
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

function loadCategories(){
    $.ajax({
        url : "/categories",
        dataType: "json",
        success : function(data) {
            $.each(data, function(index, category) {
                var categoryMenu = $("#categoryMenu");
                if (category != null) {
                    categoryMenu.show();

                    var newCollapseMenu = $(".colStub").clone();
                    var colRef = newCollapseMenu.find(".colRef");
                    colRef.prop("href", "collapse"+index);
                    colRef.text(category.title);
                    newCollapseMenu.find(".colId").prop("id", "collapse"+index);
                    $.each(category.feedUrls, function(index, url){
                        var link = newCollapseMenu.find(".colUrl");
                        link.prop("href", url);
                        link.text(url);
                        newCollapseMenu.find(".colUrlGroup").append(link);
                    });

                    newCollapseMenu.css("display","");
                    $("#accordion").append(newCollapseMenu);
                } else{
                    categoryMenu.hide();
                }
            });
        }
    });
}