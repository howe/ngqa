$(function() {
    var relativePath = '.';
    loginHTML(relativePath);
    signinHTML(relativePath);
    $.get('./me', function (json) {
        if (console && console.log){
            console.log( 'Sample of data:', $.toJSON(json) );
        }
        if (json['ok']) { //添加成功
            data = json['data'];
            $("#id").val(data['id']);
            $("#provider").val(data['provider']);
            $("#nickName").val(data['nickName']);
            $("#email").val(data['email']);
        } else {
            alert('Fail ' + data['msg']);
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
