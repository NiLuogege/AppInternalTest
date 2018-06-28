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
//            // 上传按钮
//            $("#batchUploadBtn").attr('disabled', false);
            // 上传文件按钮点击的时候
            $("#batchUploadBtn").click(function () {
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
//                console.info("上传的文件：" + fileObj);
                var FileController = "doUpload"; // 接收上传文件的后台地址
                alert("上传的文件地址：" + FileController);
                // FormData 对象
                var form = new FormData();
                // form.append("author", "hooyes"); // 可以增加表单数据
                form.append("file", fileObj); // 文件对象
                // XMLHttpRequest 对象
                var xhr = new XMLHttpRequest();
                xhr.open("post", FileController, true);
                xhr.onload = function () {
                    // ShowSuccess("上传完成");
                    alert("上传完成");
                    $("#batchUploadBtn").attr('disabled', false);
                    $("#batchUploadBtn").val("上传");
                    $("#progressBar").parent().removeClass("active");
                    $("#progressBar").parent().hide();
                    //$('#myModal').modal('hide');
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

    <%--<form id="uploadForm" action="doUpload" method="post" enctype="multipart/form-data" role="form">--%>
    <%--<div class="form-group">--%>
    <%--请选择app:<input id="selectApp" accept="application/vnd.android.package-archive" type="file" name="app"/>--%>
    <%--</div>--%>
    <%--<button type="submit" class="btn btn-info">开始上传</button>--%>

    <%--</form>--%>

    请选择app:<input id="batchFile" accept="application/vnd.android.package-archive" type="file" name="app"/>
    <button id="batchUploadBtn" type="submit" class="btn btn-info">开始上传</button>


    <div class="progress progress-striped active" style="display: none">
        <div id="progressBar" class="progress-bar progress-bar-info"
             role="progressbar" aria-valuemin="0" aria-valuenow="0"
             aria-valuemax="100" style="width: 0%">
        </div>
    </div>

</div>


</body>

</html>
