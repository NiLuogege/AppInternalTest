<%--因为bootstrap用到了html5的特性，为了正常使用，需要在最开头加上--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.css" rel="stylesheet"/>
    <script src="js/bootstrap/3.3.6/bootstrap.js"></script>
</head>

<body>
<div class="panel-body">

    <form action="doUpload" method="post">
        <table>
            <tr>
                <td>请选择app:</td>
                <td>
                    <input id="selectApp" accept="application/vnd.android.package-archive" type="file" name="app"/>
                </td>
            </tr>

            <tr class="submitTR">
                <td colspan="2" align="center">
                    <button type="submit" class="btn btn-info">开始上传</button>
                </td>
            </tr>
        </table>

    </form>

</div>


</body>

</html>
