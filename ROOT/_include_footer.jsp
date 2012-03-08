<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" %>
<div class="footer">
    <p>Coded and designed by <b>Nutz Production Committee</b>.</p>
    <p>Powered by <a href="https://github.com/nutzam/nutz">Nutz</a>.</p>
    <p>Design adapted from <a href="http://twitter.github.com/bootstrap/index.html">Bootstrap</a>.</p>
    <p><img src="https://chart.googleapis.com/chart?chs=140x140&cht=qr&choe=UTF-8&chl=<%=URLEncoder.encode(request.getRequestURL().toString())%>" /></p>
	<p><a href="http://www.miibeian.gov.cn/" title="ICP备案号">苏ICP备10226088号-17 </a></p>
</div>
