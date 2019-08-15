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

        $(function () {
            $("#okBtn").click(function () {
                var r = confirm("确认进行保存吗");
                if (r == true) {
                    var channels = $('#channelContanner').val();
                    var split = channels.split("\n");
                    var channelStr = split.join(',');
                    window.location = GetUrlPath() + "/saveChannel?channels=" + channelStr
                }
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

<div class="panel panel-success" style="max-width: 30%;margin-left: 10px">
    <div class="panel-heading">
        <h3 class="panel-title">操作指北</h3>
    </div>
    <div class="panel-body text-left text-primary">
        1. 编辑如下文档<br>
        2. 点击最下面保存按钮进行保存<br>
        3. **每个渠道需只占一行**<br>
    </div>
</div>


<div class="container" style="margin-top: 20px">

    <form role="form">
        <div class="form-group">
            <h4 style="color: #3c763d;">请增加或者删除渠道号并点击确定按钮保存</h4>
            <textarea id="channelContanner" class="form-control"
                      style="margin-top: 15px;height: 70%"></textarea>
        </div>
    </form>
</div>

<div class="text-center" style="margin-bottom: 20px">
    <button id="okBtn" class="btn btn-success btn-lg">保存啦</button>
</div>

</body>
</html>
