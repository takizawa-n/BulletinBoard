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
	<link href="./css/bulletinBoard.css" rel="stylesheet" type="text/css">

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


	<div class="logout">
		<a href="logout" onClick="return logout();">ログアウト</a>
			<c:if test="${ not empty loginUser }">
			<div>
				Login User :<c:out value="${loginUser.name}" />
			</div>
		</c:if>
	</div>



	<h1>■□　わったい菜　掲示板　□■</h1><br />




	<div class="main-contents">

		<div class="secret">
			<c:if test="${ loginUser.sectionId == 1 }">
				<a href="manage">ユーザー管理</a>
			</c:if>
		</div>
		<br />
		<br />


		<div class="posts-area">

			<a href="newPost">新しく投稿する</a>

			<div class="messages">
				<c:if test="${ not empty resultMessages }">
						<ul>
							<c:forEach items="${resultMessages}" var="resultMessage">
								<li><c:out value="${resultMessage}" />
							</c:forEach>
						</ul>
					<c:remove var="resultMessages" scope="session"/>
				</c:if>
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




			<c:forEach items="${posts}" var="post">

				<div class="onePost">

					<span class=backBlack><c:out value="${post.category}"/></span>

					<div class="post-title">
						<h2>件名：<c:out value="${post.title}" /></h2>
					</div>
					<div class="sub1">
						<span>From：<c:out value="${post.name}" /></span><br />
						<fmt:formatDate value="${post.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" />
					</div>

						<div class="text">
								<c:forTokens var="obj" items="${post.text}" delims="
								">
									<c:out value="${obj}"/><br>
								</c:forTokens>
								<br />
						</div>


						<c:if test="${(loginUser.sectionId == 2) || (loginUser.id == post.userId) ||
									((loginUser.sectionId == 3) && (loginUser.branchId == post.branchId)) ||
												(loginUser.id == post.userId)}">
							<form action="deletePost" method="post" >
								<input type="hidden" name="id" value="${post.id}">
								<p><input type="submit" value="削除する" onClick="return deletePost();"></p>
							</form>
						</c:if>
					<br />
					<br />

					<div class="newComment">
						<form action="comment" method="post"><br />
							<input type="hidden" name="postId" value="${post.id}">
							<label for="本文">この投稿にコメントする(500文字以内)</label><br />
							<textarea name="text"  cols="35" rows="5" id="text"><c:out value="${comment.text}" /></textarea><br />
							<input type="submit" value="コメントを送信" />
							<br />
						</form>
					</div>

					<div class="commentList">
						<h3>▼　Comment</h3>
							<c:forEach items="${comments}" var="comment">
								<c:if test="${comment.postId == post.id}">
									<div class="oneComment">
									<br />
										<div class="sub2">
											<span>From：<c:out value="${comment.name}" /></span><br />
											<fmt:formatDate value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" />
										</div>
										<div class="text">
											<c:forTokens var="obj" items="${comment.text}" delims="
											">
												<c:out value="${obj}"/><br>
											</c:forTokens>
											<br />
										</div>

										<c:if test="${(loginUser.sectionId == 2) || (loginUser.id == comment.userId) ||
													((loginUser.sectionId == 3) && (loginUser.branchId == comment.branchId)) ||
																(loginUser.id == comment.userId)}">
											<form action="deleteComment" method="post" >
												<input type="hidden" name="id" value="${comment.id}">
												<p><input type="submit" value="削除する" onClick="return deleteComment();"></p>
											</form>
										</c:if>
									</div>
								</c:if>
						</c:forEach>
					</div>
				</div>
			</c:forEach>
		</div>


		<div class="sort">
			<form name="sort" action="./" method="get"><br />
				<span class="sort-title">投稿しぼりこみ機能</span>
					<p>
						<label for="date">☆日付</label><br />
						<input name="startDate" type="text" id="datepicker" value="${startDate}" readonly><br />
								～　<input name="endDate" type="text" id="datepicker2" value="${endDate}" readonly><br />
						<br />
						<label for="category">☆カテゴリー</label><br />
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
					</p>
					<br />
					<input type="submit" value="しぼりこむ">
			</form>
				<br />
				<a href="./">すべての投稿を表示</a>

					<br />
						<br />

		</div>
	</div>

	<br />
	<br />
	<br />
	<div class="copyright">Copyright(c)Naoko Takizawa</div>

</body>

</html>