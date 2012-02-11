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
    <div class="container">
        <div class="center">
            <div class="questions">
                <div id="question_1" class="cell">
                    <table width="100%" cellspacing="0" cellpadding="0" border="0">
                        <tbody>
                            <tr>
                                <td valign="top">
                                    <a href="#"><img border="0" width="48" height="48" src="https://secure.gravatar.com/avatar/b75f97b497b215815971e19fdbaafa40?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-140.png"></a>
                                </td>
                                <td valign="top" style="padding-left: 12px">
                                    <span style="font-size: 16px; line-height: 130%"><a href="<%= request.getContextPath() %>/question/1">这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站</a></span>
                                    <br />
                                    <span><strong><a href="<%= request.getContextPath() %>/">tags 1</a>,&nbsp;<a href="<%= request.getContextPath() %>/">tags 2</a></strong> &nbsp;•&nbsp; <strong><a href="<%= request.getContextPath() %>/">userName</a></strong>&nbsp;•&nbsp;20 minutes ago answered by <a href="<%= request.getContextPath() %>/">userName</a></span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <jsp:include page="_include_footer.jsp" />
        </div>
    </div>
</body>
</html>
