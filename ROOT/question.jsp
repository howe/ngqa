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
    <div class="container">
        <div class="center">
            <div class="question">
                    <div class="question-header">
                        <h1 class="question-title">与twitter链接</h1>
                        <p class="question-meta">
                          2012-02-03 17:59:48&nbsp;|&nbsp;<small>question in</small><a href="/tags/test,">test</a>,<a href="/tags/markdown">markdown</a>
                        </p>
                    </div>
                    <div class="question-content">
<h2>Overview</h2>

<p><strong>Mou</strong>, the missing Markdown editor for <em>web developers</em>.</p>

<h3>Syntax</h3>

<h4>Strong and Emphasize</h4>

<p><strong>strong</strong> or <strong>strong</strong> ( Cmd + B )</p>

<p><em>emphasize</em> or <em>emphasize</em> ( Cmd + I )</p>

<p><strong>Sometimes I want a lot of text to be bold.
Like, seriously, a <em>LOT</em> of text</strong></p>

<h4>Blockquotes</h4>

<blockquote><p>Right angle brackets &gt; are used for block quotes.</p></blockquote>

<h4>Links and Email</h4>

<p>An email <a href="mailto:example@example.com">example@example.com</a> link.</p>

<p>Simple inline link <a href="http://chenluois.com">http://chenluois.com</a>, another inline link <a href="http://smallerapp.com">Smaller</a>, one more inline link with title <a title="a Safari extension" href="http://resizesafari.com">Resize</a>.</p>

<p>A <a title="Markdown editor on Mac OS X" href="http://mouapp.com">reference style</a> link. Input id, then anywhere in the doc, define the link with corresponding id:</p>
                    </div>
                    <div class="edit">
                        <a href="/2012/02/10/markdown-test/edit">edit</a>
                    </div>
            </div>
            <jsp:include page="_include_footer.jsp" />
        </div>
    </div>
</body>
</html>
