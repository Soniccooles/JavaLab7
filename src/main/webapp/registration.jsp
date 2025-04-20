<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <title>Регистрация</title>
</head>
<body>
<h1>Регистрация на сайте</h1>
<h5>Введите данные</h5>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>
<form action="register" method="post">
    <input name="username" type="text" placeholder="Логин" required>
    <input name="password" type="password" placeholder="Пароль" required>
    <input name="email" type="email" placeholder="Email" required>
    <input type="submit" class="btn btn-primary" value="Зарегистрироваться">
</form>
<a href="login">Войти</a>
</body>
</html>