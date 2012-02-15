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
    var questionTags = '';
    $.each(tags, function(index, value) {
        questionTags += String.format(',&nbsp;<a href="/tags/{0}">{1}</a>', value, value);
    });
    return questionTags.substring(1, questionTags.length - 1);
}