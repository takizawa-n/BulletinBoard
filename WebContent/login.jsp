<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ログイン</title>

</head>


<body>
<div class="main-contents">

	<c:if test="${ not empty deleteMessages }">
		<div class="deleteMessage">
			<ul>
				<c:forEach items="${deleteMessages}" var="deleteMessage">
					<li><c:out value="${deleteMessage}" />
				</c:forEach>
			</ul>
		</div>
	<c:remove var="deleteMessages" scope="session"/>
	</c:if>

	<c:if test="${ not empty errorMessages }">
		<div class="errorMessage">
			<ul>
				<c:forEach items="${errorMessages}" var="errorMessage">
					<li><c:out value="${errorMessage}" />
				</c:forEach>
			</ul>
		</div>
	<c:remove var="errorMessages" scope="session"/>
	</c:if>


<form action="login" method="post"><br />
	<label for="loginId">ログインID</label>
	<input name="loginId" value="${reqLoginId}" id="loginId"/><br />
	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/><br />
	<input type="submit" value="ログイン" /> <br />
</form>
<br />
<br />
<br />
<br />
<div class="copyright">Copyright(c)Naoko Takizawa</div>
</div>
</body>
</html>