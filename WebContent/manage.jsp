

<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理画面</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
function toWorking(){
	if(window.confirm('本当にいいんですね？')){
		location.href = "manage";
	}
	else{
		window.alert('キャンセルされました');
	}
}
function notToWorking(){
	if(window.confirm('本当にいいんですね？')){
		location.href = "manage";
	}
	else{
		window.alert('キャンセルされました');
	}
}
</script>






</head>
<body>
<div class="main-contents">

<div class="header">
	<h2>■□　ユーザー管理画面　□■</h2><br />
	<br />
	<br />
	<br />
	<a href="signup">新規登録</a>
</div>
<br />



<table border="5">
<tr><th>ログインID</th><th>名称</th><th>●</th><th>停止/復活</th><th>●</th></tr>



<tr>
<c:forEach items="${users}" var="user">
	<td><c:out value="${user.loginId}"></c:out></td>
	<td><c:out value="${user.name}"></c:out></td>
	<td><form action="settings" method="get" >
		<input type="hidden" name="userId" value="${user.id}">
		<a href="settings">編集</a>
		</form></td>
	<td><form action="manage" method="post" >
		<c:if test="${user.isWorking == 1}">
		<p><input type="button" value="停止する" onClick="return toWorking();"></p>
		</c:if>
		<c:if test="${user.isWorking == 0}">
		<p><input type="button" value="復活する" onClick="return notToWorking();"></p>
		</c:if></form></td>
	<td><form action="manage" method="post" ><a href="settings">削除する(仮)</a></form></td>
</c:forEach>
</tr>
</table>



<br />
<br />
<br />
<br />
<div class="copyright">Copyright(c)Naoko Takizawa</div>
</div>
</body>
</html>