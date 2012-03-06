$(function() {
    var relativePath = '..';
    loginHTML(relativePath);
    tagsInfoHTML(relativePath);
    signinHTML(relativePath);
    $.get(window.location + '.json', function(data) {
        if (data['ok']) {
            getQuestions(relativePath, data['data']['data']);
        }
    }, "json");
});
