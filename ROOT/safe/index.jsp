<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>安全中心</title>
		<script type"text/javascript" src="jquery-1.7.2.js"></script>
		<script type"text/javascript" src="cryptico.js"></script>
	</head>
	<body>
		<div id="reg">
			<form id="regForm">
				用户名 :<input name="nm" id="regName"></input><p/>
				密  码  :<input name="pwd" id="regPwd"></input><p/>
				<button value="注册"/>
			</form>
		</div>
		<div id="login">
			<form id="loginForm">
				用户名 :<input name="nm" id="loginName"></input><p/>
				密  码  :<input name="pwd" id="loginPwd"></input><p/>
				<button value="登录"/>
			</form>
		</div>
		<div id="user">
			用户信息: <b></b>
		</div>
		<div></div>
	</body>
</html>