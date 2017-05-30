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
	<label for="name">名前</label>
	<input name="name" value="${user.name}" id="name"/>（名前はあなたの公開プロフィールに表示されます）<br />

	<label for="account">アカウント名</label>
	<input name="account"  value="${user.account}"id="account"/>（あなたの公開プロフィール: http://localhost:8080/Chapter6/?account=アカウント名）<br />

	<label for="password">パスワード</label>
	<input name="password"  type="password" id="password"/> <br />

	<label for="email">メールアドレス</label>
	<input name="email" value="${user.email}" id="email"/> <br />

	<label for="description">説明</label>
	<textarea name="description"  cols="35" rows="5" id="description"><c:out value="${user.description}" /></textarea> <br />

	<input type="submit" value="登録" /> <br />
	<a href="./">戻る</a>
</form>
<div class="copyright">Copyright(c)Naoko Takizawa</div>
</div>
</body>
</html>
