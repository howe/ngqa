<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.nutz.ngqa.Helpers"%>
<!DOCTYPE html>
<html>
<head>
<title>Question&amp;Answer</title>
<meta charset='utf-8' />
<link href="./css/include/front.css" media="screen" rel="stylesheet" type="text/css" />
<link href="./css/include/topics.css" media="screen" rel="stylesheet" type="text/css" />
<link href="./css/login.css" media="screen" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="./js/include/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="./js/include/ICanHaz.min.js"></script>
<script type="text/javascript" src="./js/include/backbone/backbone-min.js"></script>
<script type="text/javascript" src="./js/include/backbone/underscore.js"></script>
<script type="text/javascript" src="./js/include/backbone/json2.js"></script>
<script type="text/javascript" src="./js/application.js"></script>
<script type="text/javascript" src="./js/index.js"></script>
</head>
<body data-offset="50">
  <div id="navbar"></div>
  <div class="container">
    <div id="main" class="container-fluid">
      <div class="content">
        <div class="box box_gray">
          <div class="topics" id="questions"></div>
          <div class="pagination">
            <ul>
              <li class="prev previous_page disabled"><a href="#">&#8592;上一页</a></li>
              <li class="active"><a rel="start" href="/topics?page=1">1</a></li>
              <li><a rel="next" href="/topics?page=2">2</a></li>
              <li><a href="/topics?page=3">3</a></li>
              <li><a href="/topics?page=4">4</a></li>
              <li><a href="/topics?page=5">5</a></li>
              <li class="disabled"><a href="#"><span class="gap">&hellip;</span></a></li>
              <li><a href="/topics?page=99">99</a></li>
              <li><a href="/topics?page=100">100</a></li>
              <li class="next next_page "><a rel="next" href="/topics?page=2">下一页 &#8594;</a></li>
            </ul>
          </div>
        </div>
      </div>
      <div class="sidebar">
        <div class="box">
          <%=Helpers.getInfosHtml()%>
        </div>
      </div>
    </div>
  </div>
  <a class="go_top" href="#"><i class="icon icons_go_top"></i></a>
  <footer class="footer">
    <div class="container">
      <div id="footer"></div>
    </div>
  </footer>
</body>
</html>
