<%--因为bootstrap用到了html5的特性，为了正常使用，需要在最开头加上--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.css" rel="stylesheet"/>
    <script src="js/bootstrap/3.3.6/bootstrap.js"></script>

    <title>查看apk渠道号</title>

    <script>
        $(function () {
            // 上传文件按钮点击的时候
            $("#batchUploadBtn").click(function () {
                //判断是否选择文件
                if (document.getElementById("batchFile").value == "") {
                    alert("你没有选择文件");
                    return false;
                }
                //进度条归0
                $("#progressBar").width("0%");
                //显示进度条
                $("#progressBar").parent().show();
                $("#progressBar").parent().addClass("active");

                // 上传文件
                UpladFile();
            });


            function UpladFile() {
                var fileObj = $("#batchFile").get(0).files[0]; // js 获取文件对象
                var FileController = "uploadChannelApk"; // 接收上传文件的后台地址
                // FormData 对象
                var form = new FormData();
                form.append("file", fileObj); // 文件对象
                // XMLHttpRequest 对象
                var xhr = new XMLHttpRequest();
                xhr.open("post", FileController, true);
                xhr.onload = function (data) {
                    $("#batchUploadBtn").attr('disabled', false);
                    $("#batchUploadBtn").val("上传");
                    $("#progressBar").parent().removeClass("active");
                    $("#progressBar").parent().hide();

                };


                xhr.onreadystatechange = function () {

                    if (xhr.readyState == 4) {
                        //根据服务器的响应内容格式处理响应结果
                        var result = JSON.parse(xhr.responseText);
                        //根据返回结果判断验证码是否正确

                        $("#result").text("该渠道包 " + result.result);
                    }
                }
                xhr.upload.addEventListener("progress", progressFunction, false);
                xhr.send(form);
            };

            function progressFunction(evt) {
                var progressBar = $("#progressBar");
                if (evt.lengthComputable) {
                    var completePercent = Math.round(evt.loaded / evt.total * 100) + "%";
                    progressBar.width(completePercent);
                    $("#batchUploadBtn").val("正在上传 " + completePercent);
                }
            };

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

<h2 class="text-center" style="margin-top: 20px">查看apk对应渠道号</h2>


<div class="panel panel-success" style="max-width: 30%;margin-left: 10px">
    <div class="panel-heading">
        <h3 class="panel-title">操作指北</h3>
    </div>
    <div class="panel-body text-left text-primary">
        1. 上传要查看的apk<br>
        2. 点击开始上传<br>
        3. 程序运行完毕会显示当前渠道号<br>
    </div>
</div>

<div class="panel-body">

    <div class="input-group container" style="margin-top: 20px">
        <span class="input-group-addon">请选择app:</span>
        <input id="batchFile" class="form-control" accept="application/vnd.android.package-archive" type="file"
               name="app"/>
    </div>


    <div class="container" style="margin-top: 20px">
        <h4 id="result" style="color: #ff0000;"></h4>
    </div>


    <div class="text-center">
        <button id="batchUploadBtn" style="margin-top: 50px;margin-bottom: 50px;padding-top: 20px;padding-bottom: 20px;
padding-left: 40px;padding-right: 40px;font-size: 20px" type="submit" class="btn btn-info">开始上传
        </button>
    </div>

    <div class="progress progress-striped active" style="display: none">
        <div id="progressBar" class="progress-bar progress-bar-info"
             role="progressbar" aria-valuemin="0" aria-valuenow="0"
             aria-valuemax="100" style="width: 0%">
        </div>
    </div>

</div>


</body>

</html>
