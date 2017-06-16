<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー新規登録</title>
	<link href="./css/bulletinBoard.css" rel="stylesheet" type="text/css">

</head>
<body>

<br />
<br />
	<h1>■□　ユーザー新規登録画面　□■</h1>
	<br />



<div class="main-contents">

	<div class="messages">
		<c:if test="${ not empty errorMessages }">
			<ul>
				<c:forEach items="${errorMessages}" var="errorMessage">
					<li><c:out value="${errorMessage}" />
				</c:forEach>
			</ul>
			<c:remove var="errorMessages" scope="session"/>
		</c:if>
	</div>

	<div class="input-form">
		<form action="signup" method="post"><br />
			<label for="login_id">ログインID</label>（半角英数字 6～20文字以内）<br />
			<input name="login_id" value="${user.loginId}" id="login_id"/><br />
			<br />
			<label for="password">パスワード</label>（半角英数字 6～255文字以内）<br />
			<input name="password"  type="password" id="password"/><br />
			<br />
			<label for="password2">パスワード（確認用）</label><br />
			<input name="password2"  type="password" id="password2"/><br />
			<br />
			<label for="name">名称</label>（10文字以内）<br />
			<input name="name" value="${user.name}" type="text" id="name"/><br />


			<p>
			<label for="branch_id">支店</label><br />
				<select name="branch_id">
					<c:forEach items="${branches}" var="branch">
						<c:if test="${ branch.id == user.branchId }">
							<option value="${branch.id}" selected><c:out value="${branch.name}"></c:out></option>
						</c:if>
						<c:if test="${ branch.id != user.branchId }">
							<option value="${branch.id}"><c:out value="${branch.name}"></c:out></option>
						</c:if>
					</c:forEach>
				</select>
			</p>

			<p>
			<label for="section_id">部署・役職</label><br />
				<select name="section_id">
					<c:forEach items="${sections}" var="section">
						<c:if test="${ section.id == user.sectionId }">
							<option value="${section.id}" selected><c:out value="${section.name}"></c:out></option>
						</c:if>
						<c:if test="${section.id != user.sectionId }">
							<option value="${section.id}"><c:out value="${section.name}"></c:out></option>
						</c:if>
					</c:forEach>
				</select>
			</p>
				<br />
				<br />
			<input type="submit" value="上記の内容で登録" /> <br />
		</form>
	</div>
</div>

	<a href="manage">ユーザー管理</a> | <a href="./">HOME</a>
<br />
<br />
<div class="copyright">Copyright(c)Naoko Takizawa</div>

</body>
</html>
