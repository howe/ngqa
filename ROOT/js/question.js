function splitContent(content) {
    if (!$.trim(content)) {
        return "";
    }
    var showHtml = '';
    var lines = content.split('\n');
    var pTags = [];
    var count = lines.length;
    $.each(lines, function (index, value) {
        value = $.trim(value.escapeHTML());
        if (index != count - 1) {
            if (value) {
                pTags.push(value);
            } else {
                showHtml += String.format('<p>{0}</p>', pTags.join('<br />'));
                pTags = [];
            }
        } else {
            if (pTags.length != 0) {
                pTags.push(value);
                showHtml += String.format('<p>{0}</p>', pTags.join('<br />'));
            } else {
                showHtml += String.format('<p>{0}</p>', value);
            }
        }
    });
    return showHtml;
}

$(function() {
    var relativePath = '..';
    loginHTML(relativePath);
    signinHTML(relativePath);
    $.get(window.location + '.json', function (json) {
        if (json['ok']) {
            data = json['data'];
            //代码高亮
            hljs.tabReplace = '    ';
            hljs.initHighlightingOnLoad();
            var converter = new Showdown.converter();

            var questionTamplate = '<div id="{{ id }}">\
                    <div class="row">\
                        <div class="span10">\
                            <h3>{{ title }}</h3>\
                            <div class="question-info info sep21">\
                                <span class="questioner-name">{{ questionerName }}</span>&nbsp;||&nbsp;Question at {{ time }}&nbsp;||&nbsp;{{{ tags }}}\
                            </div>\
                        </div>\
                        <div class="span1">\
                            <img class="questioner-img" src="{{ imgUrl }}" alt="{{ questionerName }}">\
                        </div>\
                    </div>\
                    <hr />\
                    <div class="question-content">{{{ content }}}</div>\
                </div>';
            var answerTamplate = '<div id="answer">\
                <div class="row" id="{{ id }}">\
                    <div class="span1">\
                        <img class="answerer-img" src="{{ imgUrl }}" alt="{{ answererName }}">\
                    </div>\
                    <div class="span10">\
                        <div class="answer-info info">\
                            <span class="answerer-name">{{ answererName }}</span><span class="answer-time">Answer at {{ time }}</span>\
                        </div>\
                        <div class="answer-content">\
                            {{{ content }}}\
                        </div>\
                    </div>\
                </div>\
                <div class="sep21"><hr /></div>\
            </div>';

            ich.addTemplate("question", questionTamplate);
            ich.addTemplate("answer", answerTamplate);
            var questionInfo = {
                questionerName: getShowUserName(data['user']),
                time: data['createdAt'],
                id: data['id'],
                title: data['title'].escapeHTML(),
                content: data.format == 'markdown' ? converter.makeHtml(data.content) : splitContent(data.content),
                tags: getTagsHTML(relativePath, data['tags']),
                imgUrl: String.format(gravatarUrl, data['user']['email'])
            };
            $("#question").append(ich.question(questionInfo));

            if (data['answers'].length != 0) {
                var answersTamplate = '<div class="row-fluid"><div class="span8 box sep21" id="answers"></div></div>';
                $(".row-fluid").first().append($(answersTamplate));
            }
            var answerInfo;
            $.each(data['answers'], function (index, value) {
                answerInfo = {
                    answererName: getShowUserName(value['user']),
                    time: value['createdAt'],
                    id: value['id'],
                    content: value.format == 'markdown' ? converter.makeHtml(value.content) : splitContent(value.content),
                    imgUrl: String.format(gravatarUrl, value['user']['email'])
                };
                $("#answers").append(ich.answer(answerInfo));
            });
        }
    }, 'json');
});
