<%--因为bootstrap用到了html5的特性，为了正常使用，需要在最开头加上--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.css" rel="stylesheet"/>
    <script src="js/bootstrap/3.3.6/bootstrap.js"></script>


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
                var FileController = "doUpload"; // 接收上传文件的后台地址
                // FormData 对象
                var form = new FormData();
                form.append("file", fileObj); // 文件对象
                var nickname = $("#nickname").val();
                form.append("nickname", nickname)
                // XMLHttpRequest 对象
                var xhr = new XMLHttpRequest();
                xhr.open("post", FileController, true);
                xhr.onload = function () {
                    alert("上传完成");
                    $("#batchUploadBtn").attr('disabled', false);
                    $("#batchUploadBtn").val("上传");
                    $("#progressBar").parent().removeClass("active");
                    $("#progressBar").parent().hide();

                    window.location = '/home'
                };
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


        });
    </script>

</head>

<body>
<div class="panel-body">

    <div class="input-group" style="margin-top: 20px">
        <span class="input-group-addon">请选择app:</span>
        <input id="batchFile" class="form-control" accept="application/vnd.android.package-archive" type="file"
               name="app"/>
    </div>

    <div class="input-group" style="margin-top: 20px">
        <span class="input-group-addon">请输入名称</span>
        <input id="nickname" type="text" class="form-control" placeholder="XHJ">
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
