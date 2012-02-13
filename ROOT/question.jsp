<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath() %>/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath() %>/css/application.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath() %>/css/login.css" />
<title>Question</title>
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
                </table>
                  <div class="form-actions">
                    <button type="submit" class="btn">Edit</button>
                  </div>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span8 box sep21">
                <table class="table">
                    <tr>
                        <td>
                            <p><a href="javascript:void(0);">@</a>user_name&nbsp;(2012-02-13 16:18:10)</p>
                            <p>这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站</p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><a href="javascript:void(0);">@</a>user_name&nbsp;(2012-02-13 16:18:10)</p>
                            <p>这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站</p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><a href="javascript:void(0);">@</a>user_name&nbsp;(2012-02-13 16:18:10)</p>
                            <p>这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站</p>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span8 box sep21">
                <form class="well">
                <legend>Answer your answer</legend>
                    <label>Content</label>
                    <textarea class="input-xxlarge" id="content" name="content" rows="10" placeholder="content"></textarea>
                    <label for="format">Format</label>
                    <select id="format" name="format">
                        <option selected="selected" value="txt">Plain Text</option>
                        <option value="markdown">Markdown</option>
                    </select>
                    <br />
                    <button type="submit" class="btn">Submit</button>
                </form>
            </div>
        </div>
        <jsp:include page="_include_footer.jsp" />
    </div>
</body>
</html>
