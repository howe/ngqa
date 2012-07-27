String.format = function() {
    if( arguments.length == 0 )
        return null;

    var str = arguments[0];
    for(var i=1;i<arguments.length;i++) {
        var re = new RegExp('\\{' + (i-1) + '\\}','gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
};

String.prototype.escapeHTML = function () {
    return this.replace(/&/g,'&amp;').replace(/>/g,'&gt;').replace(/</g,'&lt;').replace(/'/g,'&#x27;').replace(/"/g,'&quot;');
};

function getTagsHTML(relativePath, tags) {
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

var user;
function init(relativePath) {
    $.ajax({
        url: relativePath + '/me.json',
        async: false,
        success: function(data) {
            if (data['ok']) {
                user = data['data'];
            }
        },
        dataType: 'json'
    });

    $(".log-width").hide();

    $(document).mouseup(function() {
        //$(document.body).css("padding-top","60px");
        $(".log-width").hide();
    });

    footer();
}

function loginHTML(relativePath) {
    var navbarTemplate = '<div class="navbar navbar-fixed-top">\
        <div class="navbar-inner">\
            <div class="container">\
                <a class="brand" href="{{ relativePath }}/">ngqa</a>\
                    <ul class="nav">\
                        <li class="active"><a href="{{ relativePath }}/">questions</a></li>\
                        <li><a href="#">unanswered</a></li>\
                        <li id="nav_ask"><a href="{{ relativePath }}/ask.jsp">Ask!</a></li>\
                    </ul>\
                    <ul class="nav pull-right" id="signin"></ul>\
            </div>\
        </div>\
    </div>';
    ich.addTemplate("navbar", navbarTemplate);
    $("#navbar").append(ich.navbar({relativePath: relativePath}));

    var loginTemplate = '<div class="navbar-inner log-width pull-right">\
        {{#link}}\
        <a href="{{ relativePath }}/user/login/{{ name }}" class="{{ class }}" title="{{ title }}"></a>\
        {{/link}}\
    </div>';

    var login = {
        link: [
            {relativePath: relativePath, name: '#', class: 'go163', title: '与网易微博链接'},
            {relativePath: relativePath, name: '#' ,class: 'go360', title: '用360帐号登录'},
            {relativePath: relativePath, name: '#', class: 'go10000', title: '用天翼帐号登录'},
            {relativePath: relativePath, name: '#', class: 'go10010', title: '用联通帐号登录'},
            {relativePath: relativePath, name: 'douban', class: 'douban', title: '用豆瓣帐号登录'},
            {relativePath: relativePath, name: 'kaixin001', class: 'kaixin', title: '用开心网帐号登录'},
            {relativePath: relativePath, name: 'renren', class: 'renren', title: '用人人网帐号登录'},
            {relativePath: relativePath, name: 'sdo', class: 'sdo', title: '用盛大通行证登录'},
            {relativePath: relativePath, name: '#', class: 'openid', title: '用OpenID登录'},
            {relativePath: relativePath, name: '#', class: 'sohu', title: '与搜狐微博链接'},
            {relativePath: relativePath, name: '#', class: 'tianya', title: '用天涯帐号登录'},
            {relativePath: relativePath, name: '#', class: 'taobao', title: '用淘宝帐号登录'},
            {relativePath: relativePath, name: '#', class: 'twitter', title: '与twitter链接'},
            {relativePath: relativePath, name: '#', class: 'tencent', title: '与腾讯微博链接'},
            {relativePath: relativePath, name: '#', class: 'aol', title: '用AOL帐号登录'},
            {relativePath: relativePath, name: '#', class: 'browserid', title: '用Mozilla BrowserID登录'},
            {relativePath: relativePath, name: '#', class: 'foursquare', title: '用Foursquare帐号登录'},
            {relativePath: relativePath, name: '#', class: 'linkedin', title: '用Linkedin帐号登录'},
            {relativePath: relativePath, name: '#', class: 'myspace', title: '用Myspace帐号登录'},
            {relativePath: relativePath, name: '#', class: 'yammer', title: '用Yammer帐号登录'},
            {relativePath: relativePath, name: 'qq', class: 'qq', title: '用QQ帐号登录'},
            {relativePath: relativePath, name: 'yahoo', class: 'yahoo', title: '与Yahoo!连接'},
            {relativePath: relativePath, name: 'alipay', class: 'alipay', title: '支付宝快捷登录'},
            {relativePath: relativePath, name: 'baidu', class: 'baidu', title: '用baidu帐号登录'},
            {relativePath: relativePath, name: 'msn', class: 'msn', title: '用Windows Live帐号登录'},
            {relativePath: relativePath, name: 'google', class: 'google', title: '与Google连接'},
            {relativePath: relativePath, name: 'facebook', class: 'facebook', title: '用FaceBook帐号登录'},
            {relativePath: relativePath, name: 'sina', class: 'weibo', title: '与新浪微博链接'},
            {relativePath: relativePath, name: 'github', class: 'github', title: '专业程序员用Github帐号登录'}
        ]
    };
    ich.addTemplate("login", loginTemplate);
    $(".navbar-fixed-top").append(ich.login(login));
    return;
}

function footer() {
    var footerTemplate = '<p>Coded and designed by <b>Nutz Production Committee</b>.</p>\
    <p>Powered by <a href="https://github.com/nutzam/nutz">Nutz</a>.</p>\
    <p>Design adapted from <a href="http://twitter.github.com/bootstrap/index.html">Bootstrap</a>.</p>\
    <p><img width="140px" height="140px" src="https://chart.googleapis.com/chart?chs=140x140&cht=qr&choe=UTF-8&chl={{ url }}" /></p>\
    <p><a href="http://www.miibeian.gov.cn/" title="ICP备案号">苏ICP备10226088号-17 </a></p>';

    ich.addTemplate("footer", footerTemplate);
    $("#footer").append(ich.footer({url: encodeURIComponent(window.location.href)}));
    return;
}

function tagsInfoHTML(relativePath) {
    $.get(relativePath + '/tags.json', function(data) {
        if (data['ok']) {
            if (data['data'] && Object.keys(data['data']).length > 0) {
                var tagsTemplate = '<div class="box">\
                        <ul>\
                        {{#tags}}\
                            <li><a href="{{ relativePath }}/tag/{{ tagName }}">{{ tagName }}</a>({{ count }})</li>\
                        {{/tags}}\
                        </ul>\
                    </div>';
                ich.addTemplate("tags", tagsTemplate);
                var tags = [];
                $.each(data['data'], function (key, value) {
                    tags.push({tagName : key, count : value, relativePath: relativePath});
                });
                $("#infos").prepend(ich.tags({tags: tags}));
            } else {
                $("#infos").prepend($('<div class="box"><ul class="unstyled"><li><b>Not tags info now</b></li></ul></div>'));
            }
        }
    }, "json");
}

function signinHTML(relativePath) {
    if (user) {
        var template = '<p class="navbar-text pull-right">Welcome, <a href="{0}/me" class="signin1">{1}</a>&nbsp;<a href="{2}/user/logout" class="signin1">Logout</a></p>';
        $("#signin").append($(String.format(template, relativePath, getShowUserName(user), relativePath)));
    } else {
        $("#nav_ask").remove();
        $("#signin").append($('<p class="navbar-text pull-right"><a href="#signin" class="signin">signin</a></p>'));
    }
    $(".signin").click(function(e) {
        e.preventDefault();
        $(".log-width").toggle();
        $(document.body).css("padding-top", "105px");
    });
}

function getShowUserName(data) {
    var showName = data['nickName'];
    if(!showName) {
        showName = data['id'];
    }
    return showName;
}

var gravatarUrl = "http://gravatar.com/avatar/{0}.png?s=48&d=http://www.nutzam.com/wiki/img/logo.png";
function getQuestions(relativePath, data) {
	var questionTemplate = '<div class="topic topic_line">\
            <div class="pull-left avatar">\
              <img alt="1031" class="{{ questionerName }}" src="{{ imgUrl }}" style="width:48px;height:48px;" />\
            </div>\
            <div class="right_info">\
              <div class="pull-right replies">\
                <a href="/topics/4511#reply30" class="count state_false">30</a>\
              </div>\
              <div class="infos">\
                <div class="title">\
                  <a href="{{ relativePath }}/question/{{ id }}" title="{{ title }}">{{ title }}</a>\
                </div>\
                <div class="info">\
                  {{{ tags }}}\
                  •\
                  <a href="/#" data-name="{{ questionerName }}">{{ questionerName }}</a>\
                </div>\
              </div>\
            </div>\
          </div>';
    ich.addTemplate("question", questionTemplate);
    var questionInfo;
    $.each(data, function (index, value) {
        questionInfo = {
            questionerName: getShowUserName(value['user']),
            time: value['createdAt'],
            id: value['id'],
            title: value['title'].escapeHTML(),
            tags: getTagsHTML(relativePath, value['tags']),
            relativePath: relativePath,
            imgUrl: String.format(gravatarUrl, value['user']['email'])
        };
        $("#questions").append(ich.question(questionInfo));
    });
}
