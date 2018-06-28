<%--因为bootstrap用到了html5的特性，为了正常使用，需要在最开头加上--%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.css" rel="stylesheet"/>
    <script src="js/bootstrap/3.3.6/bootstrap.js"></script>
</head>

<body>
<h1>APP内部测试</h1>
<form action="upload" method="post">
    <button type="submit" class="btn btn-primary">上传应用</button>
</form>

<table>
    <tr>
        <td>名称</td>
        <td>上传日期</td>
        <td>下载地址</td>
        <td>二维码</td>
    </tr>
    <c:forEach items="${apps}" var="app">
        <tr>
            <td>${app.appName}</td>
            <td><fmt:formatDate value="${app.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><a href="/download?md5Name=${app.md5Name}">点击下载</a></td>
            <td><img src="app/qr/${app.md5Name}.png"></td>
            <td><a href="/delete?id=${app.id}">点击删除</a></td>
        </tr>
    </c:forEach>

</table>

</body>

</html>


