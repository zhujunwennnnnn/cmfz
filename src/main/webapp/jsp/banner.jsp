<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="prc" value="${pageContext.request.contextPath}"/>

<script>
    $(function () {
        $("#lbt").jqGrid({
            url: "${prc}/banner/page",
            editurl: "${prc}/banner/edit",
            //cellurl: "${prc}/banner/edit",
            //cellEdit: true,
            styleUI: "Bootstrap",
            datatype: "json",//使用json
            pager: "#pager",//分页工具
            viewrecords: true,//展示显示都多少数据
            caption: "轮播图表",//表格上下伸展
            rowNum: 5,//展示行数
            rowList: [3, 5, 10],//字定义展示行数
            pgbuttons: true,//显示分页按钮
            width: 1197,
            height: 300,
            multiselect: true,
            multiboxonly: true,
            colNames: [
                "id", "标题", "图片", "创建日期", "状态"
            ],
            colModel: [
                {name: "id", align: 'center', align: 'center'},
                {name: "title", editable: true, align: 'center'},
                {
                    name: "img", editable: true, align: 'center', edittype: 'file',
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img  style='width:100%;height:100px' src='${prc}/upload/img/" + cellvalue + "'>"
                    }
                },
                {name: "create_date", editable: true, align: 'center', edittype: 'date'},
                {
                    name: "status", editable: true, align: 'center', edittype: "select", editoptions: {
                        value: "激活:激活;未激活:未激活"
                    }
                }
            ]
        }).jqGrid("navGrid", "#pager", {search: false, addtext: "添加", edittext: "修改", deltext: "删除"}, {
            //修改
            closeAfterEdit: true,
            afterSubmit: function (response) {
                var bannerId = response.responseJSON.bannerId;
                if (bannerId == null) {
                }
                else {
                    $.ajaxFileUpload({
                        url: "${prc}/banner/upload",
                        fileElementId: "img",
                        data: {bannerId: bannerId},
                        type: "post",
                        success: function () {
                            $("#lbt").jqGrid().trigger("reloadGrid");
                            $("#upDiv").show()
                            setTimeout(function () {
                                $("#upDiv").hide()
                            }, 3000)
                        }
                    })
                }
                return response;
            }
        }, {
            //添加
            closeAfterAdd: true,
            afterSubmit: function (response) {
                var bannerId = response.responseJSON.bannerId;
                $.ajaxFileUpload({
                    url: "${prc}/banner/upload",
                    fileElementId: "img",
                    data: {bannerId: bannerId},
                    type: "post",
                    success: function () {
                        $("#lbt").jqGrid().trigger("reloadGrid");
                        $("#msgDiv").show()
                        setTimeout(function () {
                            $("#msgDiv").hide()
                        }, 3000)
                    }
                })
                return response;
            }
        }, {
            //删除
            closeAfterDel: true,
        })
    })
    
    function easypoi() {
        $.ajax({
            url:"${prc}/poi/easyOutPoi",
            success:function () {
                alert("导出成功")
            }
        })
    }
</script>
<div class="page-header">
    <h2 style="margin-top: -37px;">轮播图管理</h2>
</div>
<div class="panel panel-default">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="javascript:void(0)" aria-controls="home" role="tab" data-toggle="tab">轮播图管理</a></li>
        <li><a href="javascript:void(0)" onclick="easypoi()">导出轮播图</a></li>
    </ul>
    <div class="panel-footer">
        <div class="alert alert-success" style="display:none" id="msgDiv">
            添加成功
        </div>
        <div class="alert alert-success" style="display:none" id="upDiv">
            修改成功
        </div>
        <div class="tab-content">
            <table id="lbt">
            </table>
        </div>
        <div id="pager" style="height: 50px;"></div>
    </div>
</div>



