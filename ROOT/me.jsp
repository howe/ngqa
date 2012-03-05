<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.nutz.ngqa.Helpers" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" media="screen" href="./css/include/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="screen" href="./css/login.css" />
<link rel="stylesheet" type="text/css" media="screen" href="./css/application.css" />
<script type="text/javascript" src="./js/include/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="./js/include/ICanHaz.min.js"></script>
<script type="text/javascript" src="./js/include/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="./js/include/form2js.js"></script>
<script type="text/javascript" src="./js/include/jquery.pjax.js"></script>
<script type="text/javascript" src="./js/application.js"></script>
<script type="text/javascript"src="./js/me.js"></script>
<title>Profile</title>
</head>
<body>
    <div id="navbar"></div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span8 box">
                <form class="form-horizontal" id="user">
                    <fieldset>
                      <legend>Update your Profile</legend>
                      <div class="control-group">
                        <label for="id" class="control-label">User Id</label>
                        <div class="controls">
                          <input type="text" id="id" name="id" class="input-xlarge disabled" readonly="readonly" />
                        </div>
                      </div>
                      <div class="control-group">
                        <label for="provider" class="control-label">User Type</label>
                        <div class="controls">
                          <input type="text" id="provider" name="provider" class="input-xlarge disabled" readonly="readonly" />
                        </div>
                      </div>
                      <div class="control-group">
                        <label for="nickName" class="control-label">Nick Name</label>
                        <div class="controls">
                          <input type="text" id="nickName" name="nickName" class="input-xlarge" />
                        </div>
                      </div>
                      <div class="control-group">
                        <label for="email" class="control-label">Email</label>
                        <div class="controls">
                          <input type="text" id="email" name="email" class="input-xlarge" />
                        </div>
                      </div>
                      <div class="form-actions">
                        <button class="btn btn-primary" id="update" type="submit">Update</button>
                      </div>
                    </fieldset>
                </form>
            </div>
            <div class="span3" id="infos">
                <%= Helpers.getInfosHtml() %>
            </div>
        </div>
        <div id="footer" class="footer"></div>
    </div>
</body>
</html>
