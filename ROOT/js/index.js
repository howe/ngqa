$(function() {
    var relativePath = '.';
    loginHTML(relativePath);
    tagsInfoHTML(relativePath);
    signinHTML(relativePath);
    $.get('./question/query/list.json', function(data) {
        if (data['ok']) {
            var questionTemplate = '<tr>\
                <td class="questioner-img">\
                <img src="./img/img.jpeg" alt="{{ questioner_name }}">\
                </td>\
                <td>\
                <p>{{ questioner_name }}&nbsp;(Question at&nbsp;{{ time }})</p>\
                <p><a href="./question/{{ id }}">{{ title }}</a></p>\
                <p>{{{ tags }}}</p>\
                </td>\
                </tr>';
            ich.addTemplate("question", questionTemplate);
            var question_info;
            $.each(data['data']['data'], function (index, value) {
                question_info = {
                    questioner_name : getShowUserName(value['user']),
                    time : value['createdAt'],
                    id : value['id'],
                    title : value['title'].escapeHTML(),
                    tags : getTagsHTML(value['tags'])
                };

                $("#questions").append(ich.question(question_info));
            });
        }
    }, "json");
});
