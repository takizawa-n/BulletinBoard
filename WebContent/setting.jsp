<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー編集画面</title>
	<link href="./css/bulletinBoard.css" rel="stylesheet" type="text/css">


		<script type="text/javascript">
			function update(){
			if(window.confirm('編集内容は確認しましたか？')){
				return true;
			}else{
				window.alert('キャンセルされました');
				return false;
			}
		}
		</script>

</head>


<body>
	<br />
	<br />
	<h1>■□　ユーザー編集画面　□■</h1>
	<br />


	<div class="main-contents">
		<div class="messages">
			<c:if test="${ not empty errorMessages }">

				<ul>
					<c:forEach items="${errorMessages}" var="message">
						<li><c:out value="${message}" />
					</c:forEach>
				</ul>
				<c:remove var="errorMessages" scope="session"/>
			</c:if>
		</div>

		<div class="input-form">
			<form action="setting" method="post"><br />
				<input type="hidden" name="id" value="${editUser.id}">
					<label for="loginId">ログインID</label>（半角英数字 6～20文字以内）<br />
					<input type=text name="loginId" value="${editUser.loginId}" /><br />
					<br />
					<label for="password">パスワード</label>（半角英数字 6～255文字以内）<br />
					<input name="password" type="password" value="${password}" id="password"/> <br />
					<br />
					<label for="password2">パスワード（確認用）</label><br />
					<input name="password2" type="password" value="${password2}"  id="password2"/><br />
					<br />
					<label for="name">名称</label>（10文字以内）<br />
					<input name="name" value="${editUser.name}" id="name"/><br />

					<p>
					<label for="name">支店</label><br />
						<c:if test="${editUser.id == loginUser.id}">
							<input type="hidden" id = "branch_id" name="branch_id" value="${editUser.branchId}">
							<c:forEach items="${branches}" var="branch">
								<c:if test="${editUser.branchId == branch.id}">
									<c:out value="${branch.name}"></c:out>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${editUser.id != loginUser.id}">
							<select name="branch_id" size="1">
								<c:forEach items="${branches}" var="branch">
									<c:if test="${editUser.branchId == branch.id}">
										<option value="${branch.id}" selected><c:out value="${branch.name}"></c:out></option>
									</c:if>
									<c:if test="${editUser.branchId != branch.id}">
										<option value="${branch.id}"><c:out value="${branch.name}"></c:out></option>
									</c:if>
								</c:forEach>
							</select>
						</c:if>
					<br />
					</p>

					<p>
					<label for="name">部署・役職</label><br />
						<c:if test="${editUser.id == loginUser.id}">
							<input type="hidden" id = "section_id" name="section_id" value="${editUser.sectionId}">
							<c:forEach items="${sections}" var="section">
								<c:if test="${editUser.sectionId == section.id}">
									<c:out value="${section.name}"></c:out>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${editUser.id != loginUser.id}">
							<select name="section_id" size="1">
								<c:forEach items="${sections}" var="section">
									<c:if test="${ editUser.sectionId == section.id }">
										<option value="${section.id}" label="${section.name}" selected>
																	<c:out value="${section.name}"></c:out></option>
									</c:if>
									<c:if test="${ editUser.sectionId != section.id }">
										<option value="${section.id}" label="${section.name}" >
																	<c:out value="${section.name}"></c:out></option>
									</c:if>
								</c:forEach>
							</select>
						</c:if>
					<br />
					</p>
				<p><input type="submit" value="上記の内容で編集する" onClick="return update();" /></p><br />
			</form>
		</div>
	</div>
	<a href="manage">ユーザー管理画面</a>

	<a href="./">HOME</a>

	<div class="copyright">Copyright(c)Naoko Takizawa</div>

</body>
</html>


