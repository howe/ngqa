<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="brand" href="<%= request.getContextPath() %>/">ngqa</a>
            <div class="nav-collapse">
                <ul class="nav">
                    <li class="active"><a href="<%= request.getContextPath() %>/">questions</a></li>
                    <li><a href="/unanswered">unanswered</a></li>
                    <li><a href="#">tags</a></li>
                </ul>
                <div class="navbar-text pull-right">
                    <div class="log-width">
                        <a href="#" class="openid" title="用OpenID登录"></a>
                        <a href="#" class="google" title="与Google连接"></a>
                        <a href="#" class="msn" title="用MSN帐号登录"></a>
                        <a href="#" class="yahoo" title="与Yahoo!连接"></a>
                        <a href="#" class="twitter" title="与twitter链接"></a>
                        <a href="#" class="facebook" title="用FaceBook帐号登录"></a>
                        <a href="#" class="weibo" title="与新浪微博链接"></a>
                        <a href="#" class="qq" title="用QQ帐号登录"></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
