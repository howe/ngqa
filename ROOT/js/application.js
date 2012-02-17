String.format = function() {
    if( arguments.length == 0 )
        return null;

    var str = arguments[0];
    for(var i=1;i<arguments.length;i++) {
        var re = new RegExp('\\{' + (i-1) + '\\}','gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
};

function getTagsHTML(tags) {
    if (tags.length == 0) {
        return "";
    }
    var questionTags = [];
    $.each(tags, function(index, value) {
        value = $.trim(value);
        questionTags.push(String.format('<a href="/tags/{0}">{1}</a>', value, value));
    });
    return String.format("Question at {0}", questionTags.join(',&nbsp;'));
}

$(function() {
    $(".log-width").hide();
    $(".signin").click(function(e) {
        e.preventDefault();
        $(".log-width").toggle();
        $(document.body).css("padding-top", "105px");
    });

    $(document).mouseup(function() {
        $(document.body).css("padding-top","60px");
        $(".log-width").hide();
    });
});
