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
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BS-172bf1a3efa1482d9364f61e2923bb62", //替换为您的应用appkey
        });


        $(function () {
            var myChart = echarts.init(document.getElementById('main'));
            var option = {
                title: {
                    text: '七天用户注册量 柱状图'
                },
                tooltip: {},
                legend: {
                    data: ['注册量']
                },
                xAxis: {
                    data: ["第一天","第二天", "第三天", "第四天", "第五天", "第六天","第七天"]
                },
                yAxis: {},
                series: [{
                    name: '注册量',
                    type: 'bar',
                }],
            };
            myChart.setOption(option);
            $.ajax({
                url: "${prc}/echarts/bySeven",
                datatype: "json",
                success: function (data) {
                    myChart.setOption({
                        series: [{
                            data: data
                        }],
                    });
                }
            })
            goEasy.subscribe({
                channel: "zjw", //替换为您自己的channel
                onMessage: function (message) {
                    var parse = JSON.parse(message.content);

                    myChart.setOption({
                        series: [{
                            data: parse
                        }],
                    });
                }
            });

        })
    </script>
</head>
<body>
<div id="main" style="width: 600px;height:400px;"></div>
</body>
</html>