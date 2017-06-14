<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/i18n/jquery.ui.datepicker-ja.min.js"></script>
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/redmond/jquery-ui.css" >


	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>掲示板</title>

<script type="text/javascript">


function logout(){
	if(window.confirm('本当にログアウトしますか？')){
		return true;
	}else{
		window.alert('キャンセルされました');
		return false;
	}
}

$(function () {

	$("#datepicker").datepicker({
		maxDate: "0y"
	});

	$("#datepicker").change(function(){
		alert($("#datepicker").val());

		$("#datepicker2").datepicker("option","minDate",$("#datepicker").val())
	})

	$("#datepicker2").datepicker({
		maxDate: "0y"
	});
});

function deletePost(){
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
<a href="logout" onClick="return logout();">ログアウト</a>
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


<div class="sort">
	<form name="sort" action="./" method="get"><br />
		【　投稿絞り込み機能　】<br />
		<label for="date">日付</label><br />
		<input name="startDate" type="text" id="datepicker" value="${startDate}" readonly>　
				～　<input name="endDate" type="text" id="datepicker2" value="${endDate}" readonly>
		<br />
		<label for="category">カテゴリー</label><br />
		<select name="category">
			<option value="all"> すべて</option>
			<c:forEach items="${categories}" var="category">
				<c:if test="${ category.name != selectedCategory }">
					<option value="${category.name}" ><c:out value="${category.name}"></c:out></option>
				</c:if>
				<c:if test="${ category.name == selectedCategory }">
					<option value="${category.name}" selected ><c:out value="${category.name}"></c:out></option>
				</c:if>
			</c:forEach>
		</select>
		<br />
		<input type="submit" value="しぼりこむ">
		<input type="reset" value="入力を取り消す">
	</form>
	<br />
	<input type="button" onclick="location.href='./'" value="すべてを表示する">

</div>






<c:if test="${ not empty resultMessages }">
	<div class="resultMessage">
		<ul>
			<c:forEach items="${resultMessages}" var="resultMessage">
				<li><c:out value="${resultMessage}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="resultMessages" scope="session"/>
</c:if>


	<div class="posts">
		<c:forEach items="${posts}" var="post">
			<div class="post">
			<br />
			<br />
				<div class="user-name">
					投稿No.<span class="id"><c:out value="${post.id}" /></span><br />
					投稿者：<span class="name"><c:out value="${post.name}" /></span><br />
					カテゴリー：<span class="category"><c:out value="${post.category}" /></span>
				</div>
					★件名<div class="tiltle"><c:out value="${post.title}" /></div>
					★本文<div class="text">
							<c:forTokens var="obj" items="${post.text}" delims="
							">
								<c:out value="${obj}"/><br>
							</c:forTokens>
							<br>
					</div>
					(投稿日時  <span class="date"><fmt:formatDate value="${post.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></span>)

				<c:if test="${(loginUser.sectionId == 2) || (loginUser.id == post.userId) ||
							((loginUser.sectionId == 3) && (loginUser.branchId == post.branchId)) ||
										(loginUser.id == post.userId)}">
					<form action="deletePost" method="post" >
						<input type="hidden" name="id" value="${post.id}">
						<p><input type="submit" value="削除する" onClick="return deletePost();"></p>
					</form>
				</c:if>
			</div>
			<br />
			<br />

			<form action="comment" method="post"><br />
				<input type="hidden" name="postId" value="${post.id}">
				<label for="本文">この投稿にコメントする</label><br />
				<textarea name="text"  cols="35" rows="5" id="text"><c:out value="${comment.text}" /></textarea><br />
				<input type="submit" value="コメントを送信" />(500文字以内)<br />
				<br />
			</form>


				▼　投稿No.<c:out value="${post.id}" />（<c:out value="${post.name}" />さん）へのコメント欄
				<br>
					<c:forEach items="${comments}" var="comment">
						<c:if test="${comment.postId == post.id}">
							<div class="comment">
								<div class="name">From：<c:out value="${comment.name}" />さん</div>
								<div class="text">
									<c:forTokens var="obj" items="${comment.text}" delims="
									">
										<c:out value="${obj}"/><br>
									</c:forTokens>
									<br>
								</div>
								<div class="insertDate">
									(投稿日時  <fmt:formatDate value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" />）</div>

								<c:if test="${(loginUser.sectionId == 2) || (loginUser.id == comment.userId) ||
											((loginUser.sectionId == 3) && (loginUser.branchId == comment.branchId)) ||
														(loginUser.id == comment.userId)}">
									<form action="deleteComment" method="post" >
										<input type="hidden" name="id" value="${comment.id}">
										<p><input type="submit" value="削除する" onClick="return deleteComment();"></p>
									</form>
								</c:if>
								<br>
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