$(function() {
    //代码高亮
    hljs.tabReplace = '    ';
    hljs.initHighlightingOnLoad();

    $("#add-answer").click(function() {
        $.ajax({
            type : 'POST',
            url  : '${base}/question/${obj.id}/answer/add',
            data :  $.toJSON(form2js("answer-form")),
            dataType : 'json',
            success: function( data ) {
                if (console && console.log){
                      console.log( 'Sample of data:', $.toJSON(data) );
                }
                if (data['ok']) { //添加成功
                    window.location.reload();
                } else {
                    alert('Fail ' + data['msg']);
                }
            }
        });
    });
});
