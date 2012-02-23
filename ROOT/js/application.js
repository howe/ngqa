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

function getTagsHTML(tags) {
    if (tags.length == 0) {
        return "Not tags now";
    }
    var questionTags = [];
    $.each(tags, function(index, value) {
        value = $.trim(value.escapeHTML());
        questionTags.push(String.format('<a href="/tags/{0}">{1}</a>', value, value));
    });
    return String.format("Question at {0}", questionTags.join(',&nbsp;'));
}

$(function() {
    loginHTML();
    footer();

    $(".log-width").hide();
    $(".signin").click(function(e) {
        e.preventDefault();
        $(".log-width").toggle();
        $(document.body).css("padding-top", "105px");
    });

    $(document).mouseup(function() {
        $(document.body).css("padding-top","60px");
        $(".log-width").hide();
    });
});

function loginHTML() {
    var navbarTemplate = '<div class="navbar navbar-fixed-top">\
        <div class="navbar-inner">\
            <div class="container-fluid">\
                <a class="brand" href="./">ngqa</a>\
                <div class="nav-collapse">\
                    <ul class="nav">\
                        <li class="active"><a href="./">questions</a></li>\
                        <li><a href="#">unanswered</a></li>\
                        <li><a href="#">tags</a></li>\
                        <li><a href="./ask.jsp">Ask!</a></li>\
                    </ul>\
                    <ul class="nav pull-right">\
                        <li><a href="#signin" class="signin">signin</a></li>\
                    </ul>\
                </div>\
            </div>\
        </div>\
    </div>';
    $("#navbar").append($(navbarTemplate));

    var loginTemplate = '<div class="navbar-inner log-width pull-right">\
        {{#link}}\
        <a href="./user/login/{{ name }}" class="{{ class }}" title="{{ title }}"></a>\
        {{/link}}\
    </div>';

    var login = {
        link : [
            {name:'#', class: 'go163', title: '与网易微博链接'},
            {name:'#' ,class: 'go360', title: '用360帐号登录'},
            {name:'#', class: 'go10000', title: '用天翼帐号登录'},
            {name:'#', class: 'go10010', title: '用联通帐号登录'},
            {name:'douban', class: 'douban', title: '用豆瓣帐号登录'},
            {name:'kaixin001', class: 'kaixin', title: '用开心网帐号登录'},
            {name:'renren', class: 'renren', title: '用人人网帐号登录'},
            {name:'sdo', class: 'sdo', title: '用盛大通行证登录'},
            {name:'#', class: 'openid', title: '用OpenID登录'},
            {name:'#', class: 'sohu', title: '与搜狐微博链接'},
            {name:'#', class: 'tianya', title: '用天涯帐号登录'},
            {name:'#', class: 'taobao', title: '用淘宝帐号登录'},
            {name:'#', class: 'twitter', title: '与twitter链接'},
            {name:'#', class: 'tencent', title: '与腾讯微博链接'},
            {name:'#', class: 'aol', title: '用AOL帐号登录'},
            {name:'#', class: 'browserid', title: '用Mozilla BrowserID登录'},
            {name:'#', class: 'foursquare', title: '用Foursquare帐号登录'},
            {name:'#', class: 'linkedin', title: '用Linkedin帐号登录'},
            {name:'#', class: 'myspace', title: '用Myspace帐号登录'},
            {name:'#', class: 'yammer', title: '用Yammer帐号登录'},
            {name:'qq', class: 'qq', title: '用QQ帐号登录'},
            {name:'yahoo', class: 'yahoo', title: '与Yahoo!连接'},
            {name:'alipay', class: 'alipay', title: '支付宝快捷登录'},
            {name:'baidu', class: 'baidu', title: '用baidu帐号登录'},
            {name:'msn', class: 'msn', title: '用Windows Live帐号登录'},
            {name:'google', class: 'google', title: '与Google连接'},
            {name:'facebook', class: 'facebook', title: '用FaceBook帐号登录'},
            {name:'sina', class: 'weibo', title: '与新浪微博链接'},
            {name:'github', class: 'github', title: '专业程序员用Github帐号登录'}
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
    <p><img width="140px" height="140px" src="https://chart.googleapis.com/chart?chs=140x140&cht=qr&choe=UTF-8&chl={{ url }}" /></p>';

    ich.addTemplate("footer", footerTemplate);
    $("#footer").append(ich.footer({url: encodeURIComponent(window.location.href)}));
    return;
}
