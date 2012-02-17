function getContentHTML(converter, content, format) {
    return format == 'markdown' ? converter.makeHtml(content) : content;
}

$(function() {
    hljs.tabReplace = '    ';
    hljs.initHighlightingOnLoad();

    var converter = new Showdown.converter();
    var sample = "#### Underscores\nthis should have _emphasis_\nthis_should_not\n_nor_should_this\n\n\
#### Autolinking\na non-markdown link: http://github.com/blog\nthis one is [a markdown link](http://github.com/blog)\nEmail test: support@github.com\n\n\
#### Commit links\nc4149e7bac80fcd1295060125670e78d3f15bf2e\ntekkub@c4149e7bac80fcd1295060125670e78d3f15bf2e\nmojombo/god@c4149e7bac80fcd1295060125670e78d3f15bf2e\n\n\
#### Issue links\nissue #1\ntekkub#1\nmojombo/god#1";
    var codeStr = 'HTML:\n\n    <h1>HTML code</h1>\n    <p class="some">This is an example</p>\n\nPython:\n\n    def func():\n      for i in [1, 2, 3]:\n        print "%s" % i';

    $.get('./question/', function(json) {
        if (json['ok']) {
            var data = json['data'];
            $("#question-title").html(data['title']);
            $("#questionse-name").html(data['user']["id"]);
            $("#question-time").html(data["createdAt"]);
            $("#question-tags").html(getTagsHTML(data['tags']));
            // $("#question-content").html(getContentHTML(converter, data['content'], data['format']));
            $("#question-content").html(getContentHTML(converter, sample + '\n\n' + codeStr, 'markdown'));

            $.each(data['answers'], function (index, value) {
                var answererImgStr = '<td><img class="answerer-img" src="{0}" alt="{1}"></td>';
                var answererInfoStr = '<td><div class="answer-info"><span class="answerer-name">{0}</span><span class="answer-time">Answer at&nbsp;{1}</span></div>';
                var answerContentStr = '<div class="answer-content">{0}</div></td>';
                var html = '';
                $.each(value, function(key, value) {
                    html = String.format(answererImgStr, './img/img.jpeg', value['user']['id']);
                    html += String.format(answererInfoStr, value['user']['id'], value['createdAt']);
                    // html += String.format(answerContentStr, getContentHTML(converter, value['content'], value['format']));
                    html += String.format(answerContentStr, getContentHTML(converter, sample + '\n\n' + codeStr, 'markdown'));
                    $("#answers").append($('<tr>' + html +'</tr>'));
                });
            });
        }
    }, 'json');
});
