$(function() {
    hljs.tabReplace = '    ';
    hljs.initHighlightingOnLoad();

    var converter = new Showdown.converter();
    var sample = "#### Underscores\nthis should have _emphasis_\nthis_should_not\n_nor_should_this\n\n\
#### Autolinking\na non-markdown link: http://github.com/blog\nthis one is [a markdown link](http://github.com/blog)\nEmail test: support@github.com\n\n\
#### Commit links\nc4149e7bac80fcd1295060125670e78d3f15bf2e\ntekkub@c4149e7bac80fcd1295060125670e78d3f15bf2e\nmojombo/god@c4149e7bac80fcd1295060125670e78d3f15bf2e\n\n\
#### Issue links\nissue #1\ntekkub#1\nmojombo/god#1";
    var codeStr = 'HTML:\n\n    <h1>HTML code</h1>\n    <p class="some">This is an example</p>\n\nPython:\n\n    def func():\n      for i in [1, 2, 3]:\n        print "%s" % i';

    // $("#question-title").html("这是一个神奇的网站");
    $("#questionse-name").html("这是一个神奇的网站");
    $("#question-time").html("2012-02-15 14:54:41");
    var tags = ['question', 'answer'];
    $("#question-tags").html(getTagsHTML(tags));
    $("#question-content").html(converter.makeHtml(sample + '\n\n' + codeStr));

    var answererImgStr = '<td><img class="answerer-img" src="{0}" alt="{1}"></td>';
    var answererInfoStr = '<td><div class="answer-info"><span class="answerer-name">{0}</span><span class="answer-time">Answer at&nbsp;{1}</span></div>';
    var answerContentStr = '<div class="answer-content">{0}</div></td>';
    var html = '';
    $.each([1, 2, 3, 4], function (index, value) {
        html = String.format(answererImgStr, './img/img.jpeg', 'userName');
        html += String.format(answererInfoStr, 'UserName', '2012-02-15 16:58:19');
        html += String.format(answerContentStr, converter.makeHtml(sample+'\n\n'+ codeStr));
        $("#answers").append($('<tr>' + html +'</tr>'));
      });
});
