<%--
  Created by IntelliJ IDEA.
  User: niluogege
  Date: 2019/8/9
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="css/bootstrap/3.3.6/bootstrap.css" rel="stylesheet"/>
    <script src="js/bootstrap/3.3.6/bootstrap.js"></script>

    <title>渠道包</title>
</head>
<body>

<h1 class="text-center" style="margin-top: 20px">自动生成渠道包</h1>


<div class="panel panel-success" style="max-width: 30%;margin-left: 10px">
    <div class="panel-heading">
        <h3 class="panel-title">操作指北</h3>
    </div>
    <div class="panel-body text-left text-primary">
        这是一个基本的面板<br>
        这是一个基本的面板<br>
        这是一个基本的面板<br>
        这是一个基本的面板<br>
        这是一个基本的面板<br>
        这是一个基本的面板<br>
        这是一个基本的面板<br>

    </div>
</div>


<div class="container" style="margin-top: 20px">

    <form role="form">
        <div class="form-group">
            <h4 style="color: #3c763d;">请按规则输入渠道号</h4>
            <textarea class="form-control" rows="3" style="margin-top: 15px"></textarea>
        </div>
    </form>
</div>

<form class="text-center" style="margin-top: 30px;margin-bottom: 40px" action="startChannelApk" method="post">

    <button type="submit" class="btn btn-success  btn-lg">开始生成</button>

</form>

</body>
</html>
