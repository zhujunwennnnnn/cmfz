<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<head>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Login Form Template</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/form-elements.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" href="assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/jquery.backstretch.min.js"></script>
    <script src="assets/js/scripts.js"></script>
    <script src="../boot/js/jquery.validate.min.js"></script>
    <script type="text/javascript">

        function b1() {
            //声明正则表达式
            var pattern_userName = /^[\da-zA-Z]{3,15}$/;
            //获取表单中的数据
            var userName = document.getElementById("form-username").value;
            //清空错误提示信息
            var error = document.getElementsByClassName("error");
            for (var i = 0; i < error.length; i++) {
                error[i].innerText = "";
            }
            //判断是否合法
            if (!pattern_userName.test(userName.trim())) {
                document.getElementById("errorName").innerText = "度在2~15之间，只能包含字母、数字和下划线";
                return false;
            }
            return true;
        }
        function b2() {
            var pattern_pwd = /^[\da-zA-Z]{6,18}$/;
            var userPwd = document.getElementById("form-password").value;
            var error = document.getElementsByClassName("error");
            for (var i = 0; i < error.length; i++) {
                error[i].innerText = "";
            }
            if (!pattern_pwd.test(userPwd.trim())) {
                document.getElementById("errorPwd").innerText = "长度在6~18之间，只能包含字母、数字和下划线";
                return false;
            }
            return true

        }

        $(function () {
            $("#codeImage").click(function () {
                $("#codeImage").attr("src", "${pageContext.request.contextPath}/code/codes?time=" + new Date().getTime())
            })

            $("#loginButtonId").click(function () {
                    //正则表达式
                    var pattern_userName = /^[\da-zA-Z]{2,15}$/;
                    var pattern_pwd = /^[\da-zA-Z]{6,18}$/;
                    var pattern_yzm = /^[\da-zA-Z]{4,4}$/;
                    //获取文本框中的值
                    var userName = document.getElementById("form-username").value;
                    var userPwd = document.getElementById("form-password").value;
                    var userYzm = document.getElementById("form-code").value;
                    //清空提示信息
                    var error = document.getElementsByClassName("error");
                    for (var i = 0; i < error.length; i++) {
                        error[i].innerText = "";
                    }
                    //判断是否合法格式
                    if (!pattern_userName.test(userName.trim())) {
                        document.getElementById("errorName").innerText = "长度在2~15之间，只能包含字母、数字和下划线";
                        return false;
                    } else if (!pattern_pwd.test(userPwd.trim())) {
                        document.getElementById("errorPwd").innerText = "长度在6~18之间，只能包含字母、数字和下划线";
                        return false;
                    } else if (!pattern_yzm.test(userYzm.trim())) {
                        document.getElementById("errorYzm").innerText = "长度只能为4，只能包含字母、数字";
                        return false;
                    } else {
                        var serialize = $("#loginForm").serialize();
                        $.ajax({
                            url: "${pageContext.request.contextPath}/admin/login",
                            data: serialize,
                            datatype: "json",
                            success: function (data) {
                                if (data.struts == "success") {
                                    location.href = "firstpage.jsp"
                                } else if ("error" == data.struts) {
                                    $("#form-password").prop("value", "")
                                    $("#form-code").prop("value", "")
                                    $("#msgDiv").text("账号或密码错误")
                                } else if ("codeerror" == data.struts) {
                                    $("#form-password").prop("value", "")
                                    $("#form-code").prop("value", "")
                                    $("#msgDiv").text("验证码错误")
                                }
                            }
                        })
                        return true;
                    }
                }
            )
        })
    </script>
</head>
<body>

<!-- Top content -->
<div class="top-content">
    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>CMFZ</strong> Login Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>CMFZ</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>Login to showall</h3>
                            <p>Enter your username and password to log on:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" action="" method="post" class="login-form" id="loginForm">
                            <span id="msgDiv"></span>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input type="text" name="username" placeholder="*请输入账号..."
                                       class="form-username form-control" id="form-username" onblur="b1()">
                                <p class="error" id="errorName" style="color: red"></p>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input type="password" name="password" placeholder="*请输入密码..."
                                       class="form-password form-control" id="form-password" onblur="b2()">
                                <p class="error" id="errorPwd" style="color: red"></p>
                            </div>
                            <div class="form-group">
                                <img id="codeImage" style="height: 48px" class="captchaImage"
                                     src="${pageContext.request.contextPath}/code/codes">
                                <input style="width: 289px;height: 50px;border:3px solid #ddd;border-radius: 4px;"
                                       placeholder="*请输入验证码..."
                                       type="test" name="code" id="form-code">
                                <p class="error" id="errorYzm" style="color: red"></p>
                            </div>
                            <a href="javascript:void(0)"><input type="button"
                                                                style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;"
                                                                id="loginButtonId" value="登录"></a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>