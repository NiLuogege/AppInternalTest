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
    <style>
        .table tbody tr td{
            vertical-align: middle;
        }
    </style>
</head>

<body>
<h1 class="text-center" style="margin-top: 20px">享换机APP内部测试 </h1>
<form class="text-center" style="margin-top: 30px;margin-bottom: 40px" action="upload" method="post">
    <button type="submit" class="btn btn-info btn-lg">
        <span class="glyphicon glyphicon-cloud-upload"></span>
        上传应用
    </button>
</form>

<table class="table table-hover">
    <tr class="text-center">
        <td>名称</td>
        <td>上传日期</td>
        <td>下载地址</td>
        <td>二维码</td>
        <td>删除</td>
        <td>修改</td>
    </tr>
    <c:forEach items="${apps}" var="app">
        <tr class="text-center">
            <td>${app.nickname}</td>
            <td><fmt:formatDate value="${app.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><a href="/app/download?md5Name=${app.md5Name}">点击下载</a></td>
            <td><img src="app/qr/${app.md5Name}.png"></td>
            <td><a href="/app/delete?id=${app.id}&md5Name=${app.md5Name}">点击删除</a></td>
            <td><a href="/app/update?id=${app.id}&appName=${app.nickname}">修改</a></td>
        </tr>
    </c:forEach>

</table>

</body>

</html>


