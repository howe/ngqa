<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.nutz.ngqa.Helpers" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" media="screen" href="./css/include/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="screen" href="./css/application.css" />
<link rel="stylesheet" type="text/css" media="screen" href="./css/login.css" />
<script type="text/javascript" src="./js/include/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="./js/application.js"></script>
<script type="text/javascript" src="./js/index.js"></script>
<title>Question&amp;Answer</title>
</head>
<body>
    <jsp:include page="_include_navbar.jsp" />
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span8 box">
                <table class="table" id="questions">
                    <tr>
                        <td class="questioner-img">
                            <img src="./img/img.jpeg" alt="这是一个神奇的网站">
                        </td>
                        <td>
                            <p><a href="javascript:void(0);">@</a>user_name&nbsp;(2012-02-13 16:18:10)</p>
                            <p><a href="#">这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站</a></p>
                            <p>Question at <a href="javascript:void(0);">question</a>, &nbsp;<a href="javascript:void(0);">answer</a></p>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="span3" id="infos">
				<%= Helpers.getInfosHtml() %>
            </div>
        </div>
        <jsp:include page="_include_footer.jsp" />
    </div>
</body>
</html>
