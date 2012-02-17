<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" media="screen" href="./css/include/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="screen" href="./css/include/highlight/github.css" />
<link rel="stylesheet" type="text/css" media="screen" href="./css/application.css" />
<link rel="stylesheet" type="text/css" media="screen" href="./css/login.css" />
<script type="text/javascript" src="./js/include/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="./js/include/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="./js/include/showdown.js"></script>
<script type="text/javascript" src="./js/include/highlight.pack.js"></script>
<script type="text/javascript" src="./js/application.js"></script>
<script type="text/javascript" src="./js/question.js"></script>
<title>Question</title>
</head>
<body>
    <jsp:include page="_include_navbar.jsp" />
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span8 box">
                <div class="question">
                    <table class="table">
                        <tr>
                            <td style="width:100%;">
                                <h3 id="question-title">
这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站
                                </h3>
                                <div class="question-mate sep21">
                                    <p><span id="questionse-name">user_name</span>&nbsp;||&nbsp;Question it in <span id="question-time">2012-02-13 16:18:10</span>. &nbsp;||&nbsp;<span id="question-tags"></span></p>
                                </div>
                            </td>
                            <td class="questioner-img">
                                <img id="questioner-img" src="./img/img.jpeg" alt="这是一个神奇的网站">
                            </td>
                        </tr>
                    </table>
                    <hr />
                    <div id="question-content"></div>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn">Edit</button>
                </div>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span8 box sep21">
                <table class="table" id="answers">
                    <tr>
                        <td>
                            <img class="answerer-img" src="./img/img.jpeg" alt="username">
                        </td>
                        <td>
                                <div class="answer-info">
                                    <span class="answerer-name">user_name</span><span class="answer-time">Answer at&nbsp;2012-02-13 16:18:10</span>
                                </div>
                                <div class="answer-content">
<p>这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站这是一个神奇的网站</p></div>
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
