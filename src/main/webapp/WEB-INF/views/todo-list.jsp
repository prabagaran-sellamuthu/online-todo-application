<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>User Todo Management Application</title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">

</head>
<style>
.btn-success {
	color: #fff;
	background-color: #0076a8;
	border-color: #0076a8;
}
</style>
</head>
<body>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: #0076a8">
			<div>
				<a href="" class="navbar-brand"> Todo App</a>
			</div>

			<ul class="navbar-nav">
				<li><a
					href="<%=request.getContextPath()%>/users/${userId}/listtodos"
					class="nav-link">Todos</a></li>
			</ul>

			<ul class="navbar-nav navbar-collapse justify-content-end">
				<li><a href="<%=request.getContextPath()%>/logout"
					class="nav-link">Logout</a></li>
			</ul>
		</nav>
	</header>

	<div class="row">
		<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

		<div class="container">
			<h3 class="text-center">List of Todos</h3>
			<hr>
			<div class="container text-left">

				<a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add
					Todo</a>
			</div>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th></th>
						<th>Title</th>
						<th>Description</th>
						<th>Target Date</th>
						<th>Todo Status</th>
						<th>Last Updated Date</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<!--   for (Todo todo: todos) {  -->
					<c:forEach var="todo" items="${listTodo}">

						<tr>
							<td><input type="checkbox" name="chk[]"></td>
							<td><c:out value="${todo.title}" /></td>
							<td><c:out value="${todo.description}" /></td>
							<td><c:out value="${todo.targetDate}" /></td>
							<td><c:out value="${todo.status}" /></td>
							<td><c:out value="${todo.lastUpdatedDate}" /></td>

							<td><a
								href="<%=request.getContextPath()%>/users/${userId}/edit/${todo.id}">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp; <a
								href="<%=request.getContextPath()%>/users/${userId}/deletetodos/${todo.id}">Delete</a></td>

						</tr>
					</c:forEach>
					<!-- } -->
				</tbody>

			</table>
		</div>
	</div>
	<%@ include file="footer.html"%>
</body>
</html>
