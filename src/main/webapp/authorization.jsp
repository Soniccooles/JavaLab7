<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <title>Авторизация</title>
</head>
<body>
<h1>Авторизуйтесь на сайте</h1>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>
<form action="login" method="post">
    <input name="username" type="text" placeholder="Логин" required>
    <input name="password" type="password" placeholder="Пароль" required>
    <input type="submit" class="btn btn-primary" value="Войти">
</form>
<a href="register">Зарегистрироваться</a>
</body>
</html>