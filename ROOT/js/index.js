$(function() {
    var questionInfo = '<p>{0}&nbsp;({1})</p>';
    var questionTitle= '<p><a href="./question/{0}">{1}</a></p>';
    var tags = ['question', 'answer'];
    var html = '';
    $.each([1, 2, 3, 4], function (index, value) {
        html = String.format(questionInfo, 'user_name', '2012-02-13 16:18:10') + String.format(questionTitle, value, "这是一个神奇的网站") + String.format("<p>Question at{0}</p>", getTagsHTML(tags));
        $("#questions").append($('<tr>' + String.format('<td class="questioner-img"><img src="{0}" alt="{1}"></td>', './img/img.jpeg', 'UserName') + '<td>' +html+'</td></tr>'));
      });
});
