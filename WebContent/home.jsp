<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>掲示板</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">

<div class="header">
	<c:if test="${ empty loginUser }">
		<a href="login">ログイン</a>
		<a href="signup">登録する</a>
	</c:if>
	<c:if test="${ not empty loginUser }">

		<h1>■□　掲示板　□■</h1><br />
	<br />
	<br />
		<a href="newPost">新規投稿</a>
		<a href="manage">ユーザー管理</a>
	</c:if>
</div>


<c:if test="${ not empty loginUser }">
	<div class="profile">
		<div class="name"><h3>@<c:out value="${loginUser.name}" /></h3></div>
	</div>

<div class="messages">
	<c:forEach items="${messages}" var="message">
			<div class="message">
			<br />
			<br />
				<div class="user-name">
				投稿者：<span class="name"><c:out value="${message.name}" /></span>
				</div>
				★件名<div class="tiltle"><c:out value="${message.title}" /></div>
				★本文<div class="text"><c:out value="${message.text}" /></div>
				(投稿日時  <span class="date"><fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></span>)
			</div>
			<br />
			<br />

			<form action="comment" method="post"><br />
				<input type="hidden" name="messageId" value="${message.messageId}">
				<label for="本文">この投稿にコメントする</label><br />
				<input name="text" value="${comment.text}" id="textt" /><br />
				<input type="submit" value="コメントを送信" />(500文字以内) <br />
				<br />
			</form>

			<div onclick="obj=document.getElementById('open').style; obj.display=(obj.display=='none')?'block':'none';">
			<a style="cursor:pointer;">▼　<c:out value="${message.name}" />さんへのコメントを表示する</a>
			</div>


			<div id="open" style="display:none;clear:both;">
			<!--ここの部分が折りたたまれる＆展開される部分になります。
			自由に記述してください。-->

			<c:forEach items="${comments}" var="comment">
			<c:if test="${comment.messageId == message.messageId}">
				<div class="comment">
					<div class="name"><c:out value="${comment.name}" />さんのコメント</div>
					<div class="text"><c:out value="${comment.text}" /></div>
					<div class="insertDate"><c:out value="${comment.insertDate}" />に投稿</div>
					===================
					</div>
				</c:if>
			</c:forEach>
			</div>

			************

	</c:forEach>
</div>
</c:if>

<br />
<br />
<br />
<div class="copyright">Copyright(c)Naoko Takizawa</div>
</div>
</body>
</html>