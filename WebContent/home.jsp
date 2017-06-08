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

<script type="text/javascript">

function deleteMessage(){
	if(window.confirm('このメッセージを本当に削除しますか？')){
		return true;
	}else{
		window.alert('キャンセルされました');
		return false;
	}
}
function deleteComment(){
	if(window.confirm('このコメントを本当に削除しますか？')){
		return true;
	}else{
		window.alert('キャンセルされました');
		return false;
	}
}

</script>



</head>
<body>
<div class="main-contents">
<a href="logout">ログアウト</a>
<div class="header">
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


	<h1>■□　掲示板　□■</h1><br />
	<br />
	<br />
	<a href="newPost">新規投稿</a>
	<c:if test="${ loginUser.sectionId == 1 }">
		<a href="manage">ユーザー管理</a>
	</c:if>

</div>

<c:if test="${ not empty loginUser }">
	<div class="profile">
		<div class="name"><h3><c:out value="${loginUser.name}" />さんがログイン中です</h3></div>

	</div>
</c:if>

	<div class="messages">
		<c:forEach items="${messages}" var="message">
			<div class="message">
			<br />
			<br />
				<div class="user-name">
					投稿No.<span class="id"><c:out value="${message.id}" /></span><br />
					投稿者：<span class="name"><c:out value="${message.name}" /></span><br />
					カテゴリー：<span class="cateory"><c:out value="${message.category}" /></span>
				</div>
					★件名<div class="tiltle"><c:out value="${message.title}" /></div>
					★本文<div class="text"><c:out value="${message.text}" /></div>
					(投稿日時  <span class="date"><fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></span>)

				<c:if test="${(loginUser.sectionId == 2) || (loginUser.id == message.userId) ||
							((loginUser.sectionId == 3) && (loginUser.branchId == message.branchId)) ||
										(loginUser.id == message.userId)}">
					<form action="deleteMessage" method="post" >
						<input type="hidden" name="id" value="${message.id}">
						<p><input type="submit" value="削除する" onClick="return deleteMessage();"></p>
					</form>
				</c:if>
			</div>
			<br />
			<br />

			<form action="comment" method="post"><br />
				<input type="hidden" name="messageId" value="${message.id}">
				<label for="本文">この投稿にコメントする</label><br />
				<textarea name="text"  cols="35" rows="5" id="text"><c:out value="${comment.text}" /></textarea><br />
				<input type="submit" value="コメントを送信" />(500文字以内)<br />
				<br />
			</form>


				▼　投稿No.<c:out value="${message.id}" />（<c:out value="${message.name}" />さん）へのコメント
				<c:forEach items="${comments}" var="comment">
					<c:if test="${comment.messageId == message.id}">
						<div class="comment">
							<div class="name">From：<c:out value="${comment.name}" />さん</div>
							<div class="text"><c:out value="${comment.text}" /></div>
							<div class="insertDate"><c:out value="${comment.insertDate}" />に投稿</div>
							<c:if test="${(loginUser.sectionId == 2) || (loginUser.id == comment.userId) ||
										((loginUser.sectionId == 3) && (loginUser.branchId == comment.branchId)) ||
													(loginUser.id == comment.userId)}">
								<form action="deleteComment" method="post" >
									<input type="hidden" name="id" value="${comment.id}">
									<p><input type="submit" value="削除する" onClick="return deleteComment();"></p>
								</form>
							</c:if>
							===================
						</div>
					</c:if>
				</c:forEach>


			<br />
			************
			<br />

		</c:forEach>
	</div>


<br />
<br />
<br />
<div class="copyright">Copyright(c)Naoko Takizawa</div>
</div>
</body>
</html>