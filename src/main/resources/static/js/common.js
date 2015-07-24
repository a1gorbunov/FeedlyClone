function showAddFeed(){
    $("div#addFeed").show();
}

function validateAndHideAddFeed(){
    if ($("#newFeedValue").val().length > 0) {
        $("div#addFeed").hide();
    }
}