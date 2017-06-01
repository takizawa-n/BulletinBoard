<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">
	<h2>■□　新規投稿画面　□■</h2><br />
	<br />
	<br />
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
<form action="newPost" method="post"><br />
	<label for="title">件名(1～50文字)</label><br />
	<textarea name="title"  cols="35" rows="5" id="title"><c:out value="${post.title}" /></textarea> <br />
	<br />
	 <br />

	<label for="text">本文(1～1000文字)</label><br />
	<textarea name="text"  cols="35" rows="5" id="text"><c:out value="${post.text}" /></textarea> <br />
	 <br />
	  <br />

	<label for="category">カテゴリー(1～10文字)</label><br />
	<textarea name="category"  cols="35" rows="5" id="category"><c:out value="${post.category}" /></textarea> <br />
	 <br />
	  <br />

	<input type="submit" value="投稿する" /> <br />
	<a href="./">戻る</a>
</form>

<div class="copyright">Copyright(c)Naoko Takizawa</div>
</div>
</body>
</html>