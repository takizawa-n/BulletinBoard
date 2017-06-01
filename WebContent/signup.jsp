<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー登録</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<form action="signup" method="post"><br />
	<label for="login_id">ログインID</label>
	<input name="login_id" value="${user.login_id}" id="login_id"/><br />

	<label for="password">パスワード</label>
	<input name="password"  type="password" id="password"/><br />

	<label for="password2">パスワード（確認用）</label>
	<input name="password2"  type="password" id="password2"/><br />


	<label for="name">名称</label>
	<input name="name" value="${user.name}" type="text" id="name"/><br />

	<label for="branch_id">支店</label>
	<p>
	<select name="branchTable">
	<option value="${branches.id}">"${branches.name}"</option>
	</select>
	</p>

	<label for="section_id">部署・役職</label>
	<p>
	<select name="sectionsTable">
	<option value="${sections.id}">"${sections.name}"</option>
	</select>
	</p>


	<input type="submit" value="登録" /> <br />
	<a href="./">戻る</a>
</form>
<div class="copyright">Copyright(c)Naoko Takizawa</div>
</div>
</body>
</html>
