<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ログイン</title>
	<link href="./css/bulletinBoard.css" rel="stylesheet" type="text/css">
</head>


<body>


<div class="login">

	<h1>わったい菜	掲示板</h1>

	<div class="messages">
		<c:if test="${ not empty deleteMessages }">
				<ul>
					<c:forEach items="${deleteMessages}" var="deleteMessage">
						<li><c:out value="${deleteMessage}" />
					</c:forEach>
				</ul>
		<c:remove var="deleteMessages" scope="session"/>
		</c:if>

		<c:if test="${ not empty errorMessages }">
				<ul>
					<c:forEach items="${errorMessages}" var="errorMessage">
						<li><c:out value="${errorMessage}" />
					</c:forEach>
				</ul>
			<c:remove var="errorMessages" scope="session"/>
		</c:if>
	</div>




	<form action="login" method="post">
		<label for="loginId">ログインID</label>
		<input name="loginId" value="${reqLoginId}" id="loginId" /><br />
		<label for="password">パスワード</label>
		<input name="password" type="password" id="password"/><br />
		<br />
		<br />
		<input type="submit" value="ログイン" />
		<br />
	</form>
</div>
<br />
<br />
<br />
<br />
<div class="copyright">Copyright(c)Naoko Takizawa</div>

</body>
</html>