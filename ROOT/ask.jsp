<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath() %>/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath() %>/css/application.css" />
<title>Ask</title>
</head>
<body>
    <jsp:include page="_include_navbar.jsp" />
    <div class="container">
        <div class="center">
            <form class="form-horizontal">
                <fieldset>
                  <legend>Ask your ask</legend>
                  <div class="control-group">
                    <label class="control-label" for="title">Title</label>
                    <div class="controls">
                      <input type="text" class="input-xxlarge" id="title" name="title" placeholder="title">
                      <p class="help-block">Most input Title</p>
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label" for="tags">Tags</label>
                    <div class="controls">
                      <input type="text" class="input-xxlarge" id="tags" name="tags" placeholder="tags">
                      <p class="help-block">split by ","</p>
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label" for="content">Content</label>
                    <div class="controls">
                      <textarea class="input-xxlarge" id="content" name="content" rows="10" placeholder="content"></textarea>
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label" for="select01">Format</label>
                    <div class="controls">
                      <select id="format" name="format">
                        <option selected="selected" value="txt">Plain Text</option>
                        <option value="markdown">Markdown</option>
                        <option value="textile">Textile</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Ask</button>
                    <button type="reset" class="btn">Cancel</button>
                  </div>
                </fieldset>
            </form>
        </div>
        <jsp:include page="_include_footer.jsp" />
    </div>
</body>
</html>
