<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" media="screen" href="${base}/css/include/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${base}/css/include/highlight/github.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${base}/css/application.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${base}/css/login.css" />
<script type="text/javascript" src="${base}/js/include/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${base}/js/include/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="${base}/js/include/highlight.pack.js"></script>
<script type="text/javascript" src="${base}/js/include/form2js.js"></script>
<script type="text/javascript" src="${base}/js/application.js"></script>
<script type="text/javascript" src="${base}/js/question.js"></script>
<title>Question</title>
</head>
<body>
    <jsp:include page="../_include_navbar.jsp" />
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span8 box">
                <div class="question">
                    <table class="table">
                        <tr>
                            <td style="width:100%;">
                                <h3 id="question-title">${obj.title}</h3>
                                <div class="question-mate sep21">
                                    <p><span id="questionse-name">${obj.user.id}</span>&nbsp;||&nbsp;Question it in <span id="question-time"></span>. &nbsp;||&nbsp;<span id="question-tags"></span></p>
                                </div>
                            </td>
                            <td class="questioner-img">
                                <img id="questioner-img" src="${base}/img/img.jpeg" alt="${obj.user.id}">
                            </td>
                        </tr>
                    </table>
                    <hr />
                    <div id="question-content">${obj.content}</div>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn">Edit</button>
                </div>
            </div>
        </div>
        <c:forEach items="${obj.answers}" var="answer">
        <div class="row-fluid">
            <div class="span8 box sep21">
                <table class="table" id="answers">
                    <tr>
                        <td>
                            <img class="answerer-img" src="${base}/img/img.jpeg" alt="${answer.content}">
                        </td>
                        <td>
                                <div class="answer-info">
                                    <span class="answerer-name">${answer.user.id}</span><span class="answer-time">Answer at&nbsp;${answer.createdAt}</span>
                                </div>
                                <div class="answer-content">${answer.content}</div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        </c:forEach>
        <div class="row-fluid">
            <div class="span8 box sep21">
                <form class="well" id="answer-form" >
                <legend>Answer your answer</legend>
                    <label>Content</label>
                    <textarea class="input-xxlarge" id="content" name="content" rows="10" placeholder="content"></textarea>
                    <label for="format">Format</label>
                    <select id="format" name="format">
                        <option selected="selected" value="txt">Plain Text</option>
                        <option value="markdown">Markdown</option>
                    </select>
                    <br />
                    <button type="button" class="btn" id="add-answer">Submit</button>
                </form>
            </div>
        </div>
        <jsp:include page="../_include_footer.jsp" />
    </div>
</body>
</html>
