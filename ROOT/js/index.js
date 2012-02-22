$(function() {
        $.get('./question/query/list.json', function(data) {
        if (data['ok']) {
            var questionTemplate = '<tr>\
    <td class="questioner-img">\
        <img src="./img/img.jpeg" alt="{{ questioner_name }}">\
    </td>\
    <td>\
        <p>{{ questioner_name }}&nbsp;(Question at&nbsp;{{ question_time }})</p>\
        <p><a href="./question/{{ question_id }}">{{ question_title }}</a></p>\
        <p>{{{ tags }}}</p>\
    </td>\
</tr>';
            ich.addTemplate("question", questionTemplate);
            var question_info, question;
            $.each(data['data']['data'], function (index, value) {
                question_info = {
                    questioner_name : value['user']['id'],
                    question_time : value['createdAt'],
                    question_id : value['id'],
                    question_title : value['title'],
                    tags : ''
                };
                if (value['tags'].length == 0) {
                    question_info.tags = "Not tags now";
                } else {
                    question_info.tags = getTagsHTML(value['tags']);
                }

                question = ich.question(question_info);

                $("#questions").append(question);
            });
        }
    }, "json");
});
