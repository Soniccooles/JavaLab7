<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.net.URLEncoder" %>
<html>
<head>
    <title>Java Servlet App</title>
    <link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <h3 class="crDate">${creationDate}</h3>
    <h1 class="currentDir">${currentDir}</h1>

    <form action="logout" method="post">
        <input type="submit" value="Выйти">
    </form>
    <c:if test="${not empty parentDir}">
        <a href="files?path=${URLEncoder.encode(parentDir, 'UTF-8')}">Наверх</a>
    </c:if>


    <table>
        <thead>
            <th>Файл</th>
            <th>Размер</th>
            <th>Дата</th>
        </thead>
        <tbody>
        <c:forEach items="${files}" var="file">
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${file.isDirectory()}">
                            <img src="https://img.icons8.com/?size=100&id=843&format=png&color=000000" alt="Папка" class="icon">
                            <a href="files?path=${URLEncoder.encode(file.getAbsolutePath(), 'UTF-8')}">${file.getName()}/</a>
                        </c:when>
                        <c:otherwise>
                            <img src="https://img.icons8.com/?size=100&id=11651&format=png&color=000000" alt="Файл" class="icon">
                            <a href="download?fileDownloadPath=${URLEncoder.encode(file.getAbsolutePath(), 'UTF-8')}">${file.getName()}</a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${file.length()} байт</td>
                <td>
                    <jsp:useBean id="dateValue" class="java.util.Date"/>
                    <jsp:setProperty name="dateValue" property="time" value="${file.lastModified()}"/>
                    <fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy HH:mm"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
