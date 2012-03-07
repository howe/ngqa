$(function() {
    var relativePath = '.';
    loginHTML(relativePath);
    $.get('./me.json', function (json) {
        if (console && console.log){
            console.log( 'Sample of data:', $.toJSON(json) );
        }
        if (json['ok']) { //添加成功
            data = json['data'];
            $("#id").val(data['id']);
            $("#provider").val(data['provider']);
            $("#nickName").val(data['nickName']);
            $("#email").val(data['email']);
            var template = '<p class="navbar-text pull-right">Welcome, <a href="{0}/me" class="signin">{1}</a>&nbsp;<a href="{2}/user/logout" class="signin">Logout</a></p>';
            $("#signin").append($(String.format(template, relativePath, getShowUserName(data), relativePath)));
        } else {
            alert('Fail ' + data['msg']);
            $("#signin").append($('<p class="navbar-text pull-right"><a href="#signin" class="signin">signin</a></p>'));
        }
    }, 'json');

    $("#update").click(function() {
        $.post('./me/update', $.toJSON(form2js("user")), function (data) {
            if (console && console.log){
                console.log( 'Sample of data:', $.toJSON(data) );
            }
            if (data['ok']) { //添加成功
                window.location.reload();
            } else {
                alert('Fail ' + data['msg']);
            }
        }, 'json');
    });
});
