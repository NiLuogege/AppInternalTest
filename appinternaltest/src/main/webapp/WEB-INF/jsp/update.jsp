<%--
  Created by IntelliJ IDEA.
  User: niluogege
  Date: 2018/8/29
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.css" rel="stylesheet"/>
    <script src="js/bootstrap/3.3.6/bootstrap.js"></script>

    <title>update</title>

    <script>
        $(function () {
            // 上传文件按钮点击的时候
            $("#updateBtn").click(function () {
                //判断是否选择文件
                var newNickname = $("#newNickname").val();

                if (newNickname == "") {
                    alert("你没有填写名称");
                    return false;
                }
                window.location = GetUrlPath() + "/doUpdate?id=${app.id}&nickname=" + newNickname

            });


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
<div class="panel-body">

    <span class="input-group-addon">原名:${app.appName}</span>

    <div class="input-group" style="margin-top: 20px">
        <span class="input-group-addon">请输入新名称</span>
        <input id="newNickname" type="text" class="form-control" placeholder="XHJ">
    </div>


    <div class="text-center">
        <button id="updateBtn" style="margin-top: 50px;margin-bottom: 50px;padding-top: 20px;padding-bottom: 20px;
padding-left: 40px;padding-right: 40px;font-size: 20px" type="submit" class="btn btn-info">确认修改
        </button>
    </div>


</div>
</body>
</html>
