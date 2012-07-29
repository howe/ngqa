$(function() {
    var relativePath = '.';
    init(relativePath);
    loginHTML(relativePath);
    tagsInfoHTML(relativePath);
    signinHTML(relativePath);

    $.get('./question/query/list.json', function(data) {
        if (data['ok']) {
            questions(relativePath, data['data']['data']);
        }
    }, "json");
});

function questions(relativePath, data) {

    var User = Backbone.Model.extend({
        name: function () {
            return !this.get("nickName")? this.get("id") : this.get("nickName");
        },
        avatar: function () {
            return String.format("http://gravatar.com/avatar/{0}.png?s=48&d=http://www.nutzam.com/wiki/img/logo.png", this.get("email"));
        }
    });

    var Question = Backbone.Model.extend({
        initialize: function () {
            this.set({tagsHTML: this.tagsHTML()});
        },
        tagsHTML: function () {
            var tags = this.get("tags");
            if (tags.length == 0) {
                return "Not tags now";
            }
            var questionTags = [];
            $.each(tags, function(index, value) {
                value = $.trim(value.escapeHTML());
                questionTags.push(String.format('<a class="node" href="{0}/tag/{1}">{2}</a>', relativePath, value, value));
            });
            return questionTags.join(',&nbsp;');
        }
    });

    var questionTemplate = '<div class="topic topic_line">\
            <div class="pull-left avatar">\
              <img alt="{{ user.name }}" class="uface" src="{{ user.avatar }}" style="width:48px;height:48px;" />\
            </div>\
            <div class="right_info">\
              <div class="pull-right replies">\
                <a href="/topics/4511#reply30" class="count state_false">{{ answers.length }}</a>\
              </div>\
              <div class="infos">\
                <div class="title">\
                  <a href="{{ relativePath }}/question/{{ id }}" title="{{ title }}">{{ title }}</a>\
                </div>\
                <div class="info">\
                  {{{ tagsHTML }}}\
                  •\
                  <a href="/#" data-name="{{ user.name }}">{{ user.name }}</a>\
                </div>\
              </div>\
            </div>\
          </div>';
    ich.addTemplate("questionView", questionTemplate);

    var QuestionView = Backbone.View.extend({
        render: function() {
            return ich.questionView(this.model.toJSON());
        }
    });

    var question;
    $.each(data, function (index, value) {
        question = new Question(value);
        question.set({relativePath: '.', user: new User(value['user'])});
        $("#questions").append(new QuestionView({model: question}).render());
    });
}
