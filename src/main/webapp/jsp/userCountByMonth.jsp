<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="prc" value="${pageContext.request.contextPath}"></c:set>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/echarts/china.js"></script>
    <script>
        $(function () {
            var myChart = echarts.init(document.getElementById('main'));
            var option = {
                title: {
                    text: '1-12月 用户注册量 折现图'
                },
                tooltip: {},
                legend: {
                    data: ['注册量']
                },
                xAxis: {
                    data: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"]
                },
                yAxis: {},
                series: [{
                    name: '注册量',
                    type: 'line',
                }],
            };
            myChart.setOption(option);
            $.ajax({
                url: "${pageContext.request.contextPath}/echarts/byMonth",
                datatype: "json",
                success: function (data) {
                    console.log(data);
                    myChart.setOption({
                        series: [{
                            data: data
                        }],
                    });
                }
            })

        })
    </script>
</head>
<body>
<div id="main" style="width: 1000px;height:400px;"></div>
</body>
</html>