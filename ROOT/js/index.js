$(function() {
    var relativePath = '.';
    init(relativePath);
    loginHTML(relativePath);
    tagsInfoHTML(relativePath);
    signinHTML(relativePath);
    $.get('./question/query/list.json', function(data) {
        if (data['ok']) {
            getQuestions(relativePath, data['data']['data']);
        }
    }, "json");
});
