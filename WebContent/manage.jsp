

<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理画面</title>

<script type="text/javascript">


function toWorking(){
	if(window.confirm('本当にいいんですね？')){
		return true;
	}else{
		window.alert('キャンセルされました');
		return false;
	}
}
function notToWorking(){
	if(window.confirm('本当にいいんですね？')){
		return true;
	}else{
		window.alert('キャンセルされました');
		return false;
	}
}

function deleteUser(){
	if(window.confirm('本当に削除しますか？')){
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

	<c:if test="${ not empty deleteMessages }">
		<div class="deleteMessage">
			<ul>
				<c:forEach items="${deleteMessages}" var="deleteMessage">
					<li><c:out value="${deleteMessage}" />
				</c:forEach>
			</ul>
		</div>
	<c:remove var="deleteMessage" scope="session"/>
	</c:if>

	<c:if test="${ not empty updateMessages }">
		<div class="updateMessage">
			<ul>
				<c:forEach items="${updateMessages}" var="updateMessage">
					<li><c:out value="${updateMessage}" />
				</c:forEach>
			</ul>
		</div>
	<c:remove var="updateMessage" scope="session"/>
	</c:if>

	<c:if test="${ not empty errorMessages }">
		<div class="deleteMessage">
			<ul>
				<c:forEach items="${errorMessages}" var="errorMessage">
					<li><c:out value="${errorMessage}" />
				</c:forEach>
			</ul>
		</div>
	<c:remove var="errorMessage" scope="session"/>
	</c:if>

<div class="header">
	<h2>■□　ユーザー編集画面　□■</h2>
	<br />
	<c:if test="${ not empty loginUser }">
		<div class="profile">
			<div class="name"><h3><c:out value="${loginUser.name}" />さんがログイン中です</h3></div>
		</div>
	</c:if>
<br />
<br />

	<a href="signup">新規登録</a>
</div>
<table border="5">
<tr><th>ログインID</th><th>名称</th><th>●</th><th>停止/復活</th><th>●</th></tr>


<c:forEach items="${users}" var="user">
<tr>
	<td><c:out value="${user.loginId}"></c:out></td>
	<td><c:out value="${user.name}"></c:out><c:out value="${user.id}"></c:out></td>
	<td><form action="settings" method="get">
			<input type="hidden" id = "userId" name="userId" value="${user.id}">
			<input type="submit" value="編集" />
		</form>
	</td>
	<td><form action="manage" method="post">
		<input type="hidden" name="user_id" value="${user.id}">
			<c:if test="${user.isWorking == 1}">
				<input type="hidden" name="is_working" value="0">
				<p><input type="submit" value="停止する" onClick="return toWorking();"></p>
			</c:if>
			<c:if test="${user.isWorking == 0}">
				<input type="hidden" name="is_working" value="1">
				<p><input type="submit" value="復活する" onClick="return notToWorking();"></p>
			</c:if>
		</form>
	</td>
	<td><form action="deleteUser" method="post" >
			<input type="hidden" name="userId" value="${user.id}">
			<input type="hidden" name="userName" value="${user.name}">
			<p><input type="submit" value="削除する" onClick="return deleteUser();"></p>
		</form>
	</td>
<tr>
</c:forEach>
</table>



<br />
<br />	<a href="./">HOME</a>
<br />
<br />
<div class="copyright">Copyright(c)Naoko Takizawa</div>
</div>
</body>
</html>