<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: niluogege
  Date: 2019/8/14
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.css" rel="stylesheet"/>
    <script src="js/bootstrap/3.3.6/bootstrap.js"></script>


    <title>编辑发版渠道配置</title>

    <script>

        $("#okBtn").click(function () {
            var r = confirm("确认进行保存吗");
            if (r == true) {
//                $('#channelContanner').val();
            }

        })

        $(document).ready(function () {

            var list = ${channels};
            var channelStr = ''
            $(list).each(function (i, channel) {
                channelStr += channel + "\r\n"
            });
            $('#channelContanner').val(channelStr);
        });


    </script>

</head>
<body>

<h2 class="text-center" style="margin-top: 20px">该页面内容会用于下次发版打渠道包使用，请认真对待！！！</h2>


<div class="container" style="margin-top: 20px">

    <form role="form">
        <div class="form-group">
            <h4 style="color: #3c763d;">请增加或者删除渠道号并点击确定按钮保存</h4>
            <textarea id="channelContanner" class="form-control"
                      style="margin-top: 15px;height: 90%"></textarea>
        </div>
    </form>
</div>

<div class="text-center" style="margin-bottom: 20px">
    <button id="okBtn" type="submit" class="btn btn-success btn-lg">保存</button>
</div>

</body>
</html>
