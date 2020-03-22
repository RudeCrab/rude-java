<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页-RudeCrab</title>
</head>
<body>
<%--循环提取userList中的元素--%>
<c:forEach var="user" items="${userList}">
    <ul>
        <li>${user}</li>
    </ul>
</c:forEach>
</body>
</html>
