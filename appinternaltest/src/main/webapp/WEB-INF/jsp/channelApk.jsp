<%--
  Created by IntelliJ IDEA.
  User: niluogege
  Date: 2019/8/9
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.css" rel="stylesheet"/>
    <script src="js/bootstrap/3.3.6/bootstrap.js"></script>

    <title>渠道包</title>

    <script>
        $(function () {
            $("#startBtn").click(function () {

                //判断是否选择文件
                var channels = $("#channelContanner").val();

                if (channels == "") {
                    alert("你没有channel");
                    return false;
                }
                window.location = GetUrlPath() + "/startChannelApk?channels=" + channels
            })

            /**
             * 当前页面所在目录路径
             * 当前页面地址：http://www.abc.com/shop/page.php?id=123&s=142231233
             * 结果：http://www.abc.com/shop
             */
            function GetUrlPath() {
                var url = document.location.toString();
                if (url.indexOf("/") != -1) {
                    url = url.substring(0, url.lastIndexOf("/"));
                }
                return url;

            }
        });
    </script>
</head>
<body>

<h1 class="text-center" style="margin-top: 20px">自动生成渠道包</h1>


<div class="panel panel-success" style="max-width: 30%;margin-left: 10px">
    <div class="panel-heading">
        <h3 class="panel-title">操作指北</h3>
    </div>
    <div class="panel-body text-left text-primary">
       生成渠道包<br>
        读取渠道包信息<br>
        逗号分隔","<br>
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
            <textarea id="channelContanner" class="form-control" rows="3" style="margin-top: 15px"></textarea>
        </div>
    </form>
</div>

<div class="text-center">
    <button id="startBtn" type="submit" class="btn btn-success btn-lg">开始生成</button>
</div>


</body>
</html>
