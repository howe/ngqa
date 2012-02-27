<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.nutz.ngqa.Helpers" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" media="screen" href="${base}/css/include/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${base}/css/login.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${base}/css/application.css" />
<script type="text/javascript" src="${base}/js/include/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${base}/js/include/ICanHaz.min.js"></script>
<script type="text/javascript" src="${base}/js/application.js"></script>
<script type="text/javascript" src="${base}/js/tag.js"></script>
<title>Question</title>
</head>
<body>
    <div id="navbar"></div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span8 box">
                <table class="table" id="questions">
                </table>
            </div>
            <div class="span3" id="infos">
                <%= Helpers.getInfosHtml() %>
            </div>
        </div>
        <div id="footer" class="footer"></div>
    </div>
</body>
</html>
