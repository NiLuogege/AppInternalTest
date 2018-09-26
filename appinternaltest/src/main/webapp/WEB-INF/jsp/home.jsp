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

    <script>
        $(function () {
            $(".pimg").click(function () {
                var _this = $(this);//将当前的pimg元素作为_this传入函数
                imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
            });
        });

        function imgShow(outerdiv, innerdiv, bigimg, _this) {
            var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
            $(bigimg).attr("src", src);//设置#bigimg元素的src属性

            /*获取当前点击图片的真实大小，并显示弹出层及大图*/
            $("<img/>").attr("src", src).load(function () {
                var windowW = $(window).width();//获取当前窗口宽度
                var windowH = $(window).height();//获取当前窗口高度
                var realWidth = this.width;//获取图片真实宽度
                var realHeight = this.height;//获取图片真实高度
                var imgWidth, imgHeight;
                var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

                if (realHeight > windowH * scale) {//判断图片高度
                    imgHeight = windowH * scale;//如大于窗口高度，图片高度进行缩放
                    imgWidth = imgHeight / realHeight * realWidth;//等比例缩放宽度
                    if (imgWidth > windowW * scale) {//如宽度扔大于窗口宽度
                        imgWidth = windowW * scale;//再对宽度进行缩放
                    }
                } else if (realWidth > windowW * scale) {//如图片高度合适，判断图片宽度
                    imgWidth = windowW * scale;//如大于窗口宽度，图片宽度进行缩放
                    imgHeight = imgWidth / realWidth * realHeight;//等比例缩放高度
                } else {//如果图片真实高度和宽度都符合要求，高宽不变
                    imgWidth = realWidth;
                    imgHeight = realHeight;
                }
                $(bigimg).css("width", imgWidth);//以最终的宽度对图片缩放

                var w = (windowW - imgWidth) / 2;//计算图片与窗口左边距
                var h = (windowH - imgHeight) / 2;//计算图片与窗口上边距
                $(innerdiv).css({"top": h, "left": w});//设置#innerdiv的top和left属性
                $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
            });

            $(outerdiv).click(function () {//再次点击淡出消失弹出层
                $(this).fadeOut("fast");
            });
        }
    </script>

    <style>
        .table tbody tr td {
            vertical-align: middle;
        }

        .pimg {

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
            <td><img class="pimg" src="app/qr/${app.md5Name}.png"></td>
            <td><a href="/app/delete?id=${app.id}&md5Name=${app.md5Name}">点击删除</a></td>
            <td><a href="/app/update?id=${app.id}&appName=${app.nickname}">修改</a></td>
        </tr>
    </c:forEach>

</table>

<div id="outerdiv"
     style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
    <div id="innerdiv" style="position:absolute;"><img id="bigimg" style="border:5px solid #fff;" src=""/></div>
</div>


</body>

</html>


