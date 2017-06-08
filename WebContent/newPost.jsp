<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿</title>
</head>
<body>
<div class="main-contents">

<h2>■□　新規投稿画面　□■</h2><br />
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
	<label for="title">タイトル(1～50文字)</label><br />
	<input name="title" value="${post.title}" id="title" /><br />
	<br />
	 <br />

	<label for="text">本文(1～1000文字)</label><br />
	<textarea name="text"  cols="35" rows="5" id="text"><c:out value="${post.text}" /></textarea> <br />
	 <br />
	  <br />

	<p>
	<label for="category">カテゴリー</label><br />
	<select name="category">
		<option value="new"> 新しくカテゴリーを入力する→</option>
		<c:forEach items="${categories}" var="category">
			<c:if test="${ category.name != post.category }">
				<option value="${category.name}" ><c:out value="${category.name}"></c:out></option>
			</c:if>
			<c:if test="${ category.name == post.category }">
				<option value="${category.name}" selected ><c:out value="${category.name}"></c:out></option>
			</c:if>
		</c:forEach>
	</select>
	<input name="newCategory" value="${post.category}" id="category" />(1～10文字)

	 <br />
	  <br />
	 <br />
	  <br />

	<input type="submit" value="投稿する" />
	<br />
	 <br />
	  <br />
	  <br />
	  <br />

	<a href="./">HOME</a>
</form>

<div class="copyright">Copyright(c)Naoko Takizawa</div>
</div>
</body>
</html>