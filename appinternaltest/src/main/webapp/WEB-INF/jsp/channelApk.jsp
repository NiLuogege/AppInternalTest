<%--
  Created by IntelliJ IDEA.
  User: niluogege
  Date: 2019/8/9
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                var vers = $('#selectVersion').text();

                if (channels == "") {
                    alert("你没有channel=");
                    return false;
                }


                if (vers == "") {
                    alert("vers为空");
                    return false;
                }

                window.location = GetUrlPath() + "/startChannelApk?channels=" + channels + "&version=" + vers
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


            $('#vC button').on('click', function () {
                $('#selectVersion').text($(this).text())
            })



            $("#lookChannles").click(function () {
                window.location = GetUrlPath() + "/lookChannle"
            })


            $("#editChannelFile").click(function () {
                window.location = GetUrlPath() + "/editChannelFile"
            })

        });

    </script>
</head>
<body>

<h1 class="text-center" style="margin-top: 20px">自动生成渠道包</h1>

<div>

    <div class="panel panel-success" style="max-width: 30%;margin-left: 10px">
        <div class="panel-heading">
            <h3 class="panel-title">操作指北</h3>
        </div>
        <div class="panel-body text-left text-primary">
            1. 选择要生成渠道包的版本<br>
            2. 输入渠道号使用---逗号","分隔<br>
            3. 点击最下面的“开始生成”按钮<br>
            4. 进行等待 系统会自动进行操作并下载<br>
            5. 解压自动下载的压缩包<br>
            6. ***希望在使用前安装一下渠道包保证可以正常使用在进行投放***<br>

        </div>
    </div>

    <div style="width: 240px;position: absolute;top: 80px;left: 85%">

        <button id="lookChannles" type="button" class="btn btn-info">
            查看apk渠道号-->
        </button>

        <button id="editChannelFile" type="button" class="btn btn-info" style=" margin-top: 10px">
           编辑发版渠道配置-->
        </button>

    </div>

</div>

<div class="container">

    <h4 style="color: #3c763d;">当前支持生成渠道包的版本(点击更换)</h4>

    <div id="vC" style="background: #f8f8f8;width: 240px;padding: 15px">
        <c:forEach items="${versions}" var="version" varStatus="status">

            <button type="button" class="btn btn-default" style="color: #337ab7;">V ${version}</button>
            <br>

        </c:forEach>
    </div>

</div>

<div class="container" style="margin-top: 20px">

    <h4 style="color: #3c763d;">当前选中版本</h4>

    <h4 id="selectVersion" style="color: #ff0000;">V ${versions[versions.size()-1]}</h4>

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
