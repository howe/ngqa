<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath() %>/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath() %>/css/application.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath() %>/css/login.css" />
<title>Question&amp;Answer</title>
</head>
<body>
    <jsp:include page="_include_navbar.jsp" />
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span8 box">
                <table class="table">
                    <tr>
                        <td>
                            <p><a href="javascript:void(0);">@</a>user_name&nbsp;(2012-02-13 16:18:10)</p>
                            <p>这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站</p>
                            <p>Question at <a href="javascript:void(0);">question</a>, &nbsp;<a href="javascript:void(0);">answer</a></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><a href="javascript:void(0);">@</a>user_name&nbsp;(2012-02-13 16:18:10)</p>
                            <p>这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站</p>
                            <p>Question at <a href="javascript:void(0);">question</a>, &nbsp;<a href="javascript:void(0);">answer</a></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><a href="javascript:void(0);">@</a>user_name&nbsp;(2012-02-13 16:18:10)</p>
                            <p>这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站</p>
                            <p>Question at <a href="javascript:void(0);">question</a>, &nbsp;<a href="javascript:void(0);">answer</a></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><a href="javascript:void(0);">@</a>user_name&nbsp;(2012-02-13 16:18:10)</p>
                            <p>这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站</p>
                            <p>Question at <a href="javascript:void(0);">question</a>, &nbsp;<a href="javascript:void(0);">answer</a></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><a href="javascript:void(0);">@</a>user_name&nbsp;(2012-02-13 16:18:10)</p>
                            <p>这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站</p>
                            <p>Question at <a href="javascript:void(0);">question</a>, &nbsp;<a href="javascript:void(0);">answer</a></p>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="span3 box" id="infos">
<h3>广告位招租</h3>

<p>详情见<a href="http://howe.im">http://howe.im</a></p>

<p>PS: <br />
此项目用到了 nutz-web nutz-mongo nutz-web <br />
还将会用到 nutz-social ╮(╯▽╰)╭ <br />
本项目主要 贡献者 @wendal (兽) @ywjno (温泉) <br />
本人只是个监工而已</p>

<p>TODO List:</p>

<ul>
<li><p>页面:</p>

<ol><li>问题创建</li>
<li>问题查询</li>
<li>用户个人信息管理</li>
<li>管理控制台--权限管理</li>
<li>管理控制台--系统配置</li>
<li>App管理</li></ol></li>
<li><p>OpenAPI(服务端Http接口)</p>

<ol><li>通用查询接口--Question</li>
<li>权限管理</li>
<li>升级机制</li>
<li>Question全文索引</li>
<li>回答问题/关注/取消关注/评论</li>
<li>系统配置管理</li>
<li>用户登录 -- OAuth1/OAuth2</li>
<li>App鉴权及管理</li>
<li>邮件提醒</li>
<li>访问统计</li></ol></li>
<li><p>SDK-Java</p>

<ol><li>实现OpenAPI</li>
<li>App基础框架</li></ol></li>
<li><p>RSS</p>

<ol><li>实现RSS输出</li></ol></li>
<li><p>其他SDK</p></li>
</ul>
            </div>
        </div>
        <jsp:include page="_include_footer.jsp" />
    </div>
</body>
</html>
