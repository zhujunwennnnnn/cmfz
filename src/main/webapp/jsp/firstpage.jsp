<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="prc" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>首页</title>
    <%-- bootstrap的核心css --%>
    <link rel="stylesheet" href="${prc}/boot/css/bootstrap.min.css">
    <%-- jqGrid --%>
    <link rel="stylesheet" href="${prc}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%-- jquery.js --%>
    <script src="${prc}/boot/js/jquery-2.2.1.min.js"></script>
    <%-- bootstrap.js --%>
    <script src="${prc}/boot/js/bootstrap.min.js"></script>
    <%-- 国际化 --%>
    <script src="${prc}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <%-- jqGrid核心js --%>
    <script src="${prc}/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <%--上传--%>
    <script src="${prc}/boot/js/ajaxfileupload.js"></script>
    <script charset="UTF-8" src="${prc}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="UTF-8" src="${prc}/kindeditor/lang/zh-CN.js"></script>
    <script>
        $(function () {
            $.ajax({
                url: "${prc}/banner/pc",
                datatype: "json",
                success: function (data) {
                    var all = data.all;
                    for (var i = 0; i < all.length; i++) {
                        if (i == 0) {
                            var li = $("<li data-target='#carousel-example-generic' data-slide-to='" + i + "' class='active'></li>");
                            $("#co").append(li);
                            var qq = $("<div class='item active'><img style='height:362px; width:100%' src='${prc}/upload/img/" + all[i].img + "'/></div>");
                            $("#mb").append(qq);
                        } else {
                            var li = $("<li data-target='#carousel-example-generic' data-slide-to='" + i + "'></li>");
                            $("#co").append(li);
                            var qq = $("<div class='item'><img style='height:362px; width:100%' src='${prc}/upload/img/" + all[i].img + "'/></div>");
                            $("#mb").append(qq);
                        }
                    }
                }
            })
        })
        function outPoi() {
            $.ajax({
                url:"${prc}/poi/outPoi",
                success:function () {
                    alert("导出成功")
                }
            })
        }
        
    </script>
</head>
<body>

<div class="container-fluid">
    <%--导航栏--%>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-coll apse-3">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${prc}/jsp/firstpage.jsp">持名法州管理系统</a>
            </div>
            <div class="navbar-collapse" id="bs-example-navbar-collapse-3">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a><button onclick="outPoi()">导出</button></a>
                    </li>
                    <li><a href="javascript:void(0)">欢迎:<u>${sessionScope.Admin.username}</u></a></li>
                    <li><a><span class="glyphicon glyphicon-log-out"></span>退出登入</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <%--手风琴--%>
    <div class="col-sm-2">
        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
            <%--1号--%>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingOne">
                    <h4 class="panel-title" style="text-align:center;">
                        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                            用户管理
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse ">
                    <div class="panel-body">
                        <div class="btn-group btn-group-justified">
                            <div class="btn-group" role="group">
                                <a href="#table" data-toggle="tab">
                                    <button class="btn">
                                        用户列表
                                    </button>
                                </a>
                                <br>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--2号--%>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingTwo">
                    <h4 class="panel-title" style="text-align:center;">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            上师管理
                        </a>
                    </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                    <div class="panel-body">
                        <div class="btn-group btn-group-justified">
                            <div class="btn-group" role="group">
                                <a href="#table" data-toggle="tab">
                                    <button class="btn">
                                        上师管理
                                    </button>
                                </a>
                                <br>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--3号--%>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingThree">
                    <h4 class="panel-title" style="text-align:center;">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                            文章管理
                        </a>
                    </h4>
                </div>
                <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                    <div class="panel-body">
                        <a href="javascript:$('#main').load('article.jsp');" class="btn btn-default form-control">
                            文章列表
                        </a>
                    </div>
                </div>
            </div>
            <%--4号--%>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingFore">
                    <h4 class="panel-title" style="text-align:center;">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapseFore" aria-expanded="false" aria-controls="collapseFore">
                            专辑管理
                        </a>
                    </h4>
                </div>
                <div id="collapseFore" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFore">
                    <div class="panel-body">
                        <a href="javascript:$('#main').load('album.jsp');" class="btn btn-default form-control">
                            专辑列表
                        </a>
                    </div>
                </div>
            </div>
            <%--5号--%>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingFive">
                    <h4 class="panel-title" style="text-align:center;">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                            轮播图管理
                        </a>
                    </h4>
                </div>
                <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
                    <div class="panel-body">
                        <a href="javascript:$('#main').load('banner.jsp');" class="btn btn-default form-control">
                            轮播图列表
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%--巨幕 and 轮播图--%>
    <div class="col-sm-10" id="main">
        <div class="tab-content">
            <div class="tab-pane active">
                <%--巨幕--%>
                <div class="jumbotron">
                    <h3>欢迎来到持名法州后台管理系统</h3>
                </div>
                <div id="carousel-example-generic" class="carousel slide" data-interval=2000 data-ride="carousel">
                    <!-- Indicators -->
                    <ol class="carousel-indicators" id="co">

                    </ol>
                    <!-- Wrapper for slides -->
                    <div class="carousel-inner" role="listbox" id="mb">

                    </div>
                    <!-- Controls -->
                    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<%--页尾--%>
<div class="container-fluid">
    <nav class="navbar navbar-default navbar-fixed-bottom">
        <div class="container" style="text-align: center;margin-top: 25px;">
            @百知教育 baizhi@zparkir.com.cn
        </div>
    </nav>
</div>
</body>
</html>
