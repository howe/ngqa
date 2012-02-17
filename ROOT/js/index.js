$(function() {
    var questionInfoStr = '<p>{0}&nbsp;({1})</p>';
    var questionTitleStr = '<p><a href="./question/{0}.shtml">{1}</a></p>';
    var questionTagsStr = '<p></p>';
    var html = '';

    $.get('./question/query/list', function(data) {
        if (data['ok']) {
            $.each(data['data']['data'], function (index, value) {
                html = String.format(questionInfoStr, value['user']['id'], value['createdAt']);
                html += String.format(questionTitleStr, value['id'], value['title']);
                html += String.format(questionTagsStr, getTagsHTML(value['tags']));
                html = String.format('<td class="questioner-img"><img src="{0}" alt="{1}"></td>', './img/img.jpeg', value['user']['id']) + '<td>' + html + '</td>';
                $("#questions").append($('<tr>' + html +'</tr>'));
            });
        }
    }, "json");
});
