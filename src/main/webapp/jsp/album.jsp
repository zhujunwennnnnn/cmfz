<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="prc" value="${pageContext.request.contextPath}"/>

<style>
    .modal-body {
        position: relative;
        padding: 21px 130px;
    }
</style>

<script>
    $(function () {
        $("#tab").jqGrid({
            url: "${prc}/album/page",
            editurl: "${prc}/album/edit",
            styleUI: "Bootstrap",
            datatype: "json",
            pager: "#pager",
            viewrecords: true,
            caption: "专辑表",
            rowNum: 5,
            rowList: [3, 5, 9],
            pgbuttons: true,
            width: 1180,
            height: 400,
            colNames: [
                "id", "title", "score", "author", "broadcaster", "counts", "brief", "create_date", "status", "img"
            ],
            colModel: [
                {name: "id", align: 'center'},
                {name: "title", editable: true, align: 'center'},
                {name: "score", editable: true, align: 'center'},
                {name: "author", editable: true, align: 'center'},
                {name: "broadcaster", editable: true, align: 'center'},
                {name: "counts", align: 'center'},
                {name: "brief", editable: true, align: 'center'},
                {name: "create_date", editable: true, align: 'center', edittype: 'date'},
                {
                    name: "status", editable: true, align: 'center', edittype: 'select', editoptions: {
                        value: "可用:可用;不可用:不可用"
                    }
                }, {
                    name: "img", editable: true, edittype: 'file',
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img  style='width:100%;height:50px' src='${prc}/upload/img/" + cellvalue + "'>"
                    }
                }
            ],
            subGrid: true, //开启子表格
            subGridRowExpanded: function (subGridId, albumId) {
                addSubGridTable(subGridId, albumId)
            }
        }).jqGrid("navGrid", "#pager", {search: false, addtext: "添加", edittext: "修改", deltext: "删除"},
            {
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var albumId = response.responseJSON.albumId;
                    if (albumId == null) ;
                    else {
                        $.ajaxFileUpload({
                            url: "${prc}/album/upload",
                            fileElementId: "img",
                            data: {albumId: albumId},
                            type: "post",
                            success: function () {
                                $("#tab").jqGrid().trigger("reloadGrid")
                                $("#msgDiv").html(response.responseJSON.msg).show()
                                setTimeout(function () {
                                    $("#msgDiv").hide()
                                }, 4000)
                            }
                        })
                    }
                    return response
                }
            }, {
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var albumId = response.responseJSON.albumId;
                    if (albumId == null) ;
                    else {
                        $.ajaxFileUpload({
                            url: "${prc}/album/upload",
                            fileElementId: "img",
                            data: {albumId: albumId},
                            type: "post",
                            success: function () {
                                $("#tab").jqGrid().trigger("reloadGrid")
                                $("#msgDiv").html(response.responseJSON.msg).show()
                                setTimeout(function () {
                                    $("#msgDiv").hide()
                                }, 4000)
                            }
                        })
                    }
                    return response;
                }
            }, {
                closeAfterDel: true,
                afterComplete: function (response) {
                    $("#tab").jqGrid().trigger("reloadGrid")
                    $("#msgDiv").html(response.responseJSON.msg).show()
                    setTimeout(function () {
                        $("#msgDiv").hide()
                    }, 4000)
                }
            }
        )
    })

    function addSubGridTable(subGridId, albumId) {
        //动态table id
        var tableId = subGridId + "table"
        //动态div id
        var divId = subGridId + "div"
        //动态添加子表格
        $("#" + subGridId).html("<table id='" + tableId + "'></table>" +
            "<div id='" + divId + "' style='height:50px;'></div>"
        )
        $("#" + tableId).jqGrid({
            url: "${prc}/chapter/page?id=" + albumId,
            editurl: "${prc}/chapter/edit?albumId=" + albumId,
            styleUI: "Bootstrap",
            datatype: "json",
            pager: "#" + divId,
            viewrecords: true,//展示显示都多少数据
            caption: "章节表",
            toolbar: [true, "top"],
            rowNum: 5,
            rowList: [3, 5, 9],
            width: 1115,
            pgbuttons: true,
            colNames: [
                "id", "title", "album_id", "sizes", "duration", "src", "status"
            ],
            colModel: [
                {name: "id", align: 'center'},
                {name: "title", editable: true, align: 'center'},
                {name: "album_id", align: 'center'},
                {name: "sizes", align: 'center'},
                {name: "duration", align: 'center'},
                {
                    name: "src", editable: true, align: 'center', edittype: 'file', width: 300,
                    formatter: function (value, option, rows) {
                        return "<audio style='width:260px;height:40px;' controls loop preload='auto'>\n" +
                            "<source src='${prc}/upload/music/" + value + "' type='audio/mpeg' id='src" + divId + "'>\n" +
                            "<source src='${prc}/upload/music/" + value + "' type='audio/ogg'>\n" +
                            "</audio>";
                    }
                },
                {
                    name: "status", editable: true, align: 'center', edittype: 'select', editoptions: {
                        value: "激活:激活;未激活:未激活"
                    }
                }
            ]
        }).jqGrid("navGrid", "#" + divId, {search: false, addtext: "添加", edittext: "修改", deltext: "删除"}, {
            closeAfterEdit: true,
            afterSubmit: function (response) {
                var chapterId = response.responseJSON.chapterId;
                if (chapterId == null) ;
                else {
                    $.ajaxFileUpload({
                        url: "${prc}/chapter/upload",
                        fileElementId: "src",
                        data: {chapterId: chapterId},
                        type: "post",
                        success: function () {
                            $("#tab").jqGrid().trigger("reloadGrid");
                            $("#" + tableId).jqGrid().trigger("reloadGrid");
                            $("#msgDiv").html(response.responseJSON.msg).show()
                            setTimeout(function () {
                                $("#msgDiv").hide()
                            }, 4000)
                        }
                    })
                }
                return response
            }

        }, {
            closeAfterAdd: true,
            afterSubmit: function (response) {
                var chapterId = response.responseJSON.chapterId;
                if (chapterId == null) ;
                else {
                    $.ajaxFileUpload({
                        url: "${prc}/chapter/upload",
                        fileElementId: "src",
                        data: {chapterId: chapterId},
                        type: "post",
                        success: function () {
                            $("#" + tableId).jqGrid().trigger("reloadGrid");
                            $("#msgDiv").html(response.responseJSON.msg).show()
                            setTimeout(function () {
                                $("#msgDiv").hide()
                            }, 4000)
                        }
                    })
                }
                return response
            }
        }, {
            closeAfterDel: true,
            afterComplete: function () {
                $("#tab").jqGrid().trigger("reloadGrid");
                $("#msgDiv").html(response.msg).show()
                setTimeout(function () {
                    $("#msgDiv").hide()
                }, 4000)
            }
        })
        //添加按钮
        $("#t_" + tableId).html("<button class='btn btn-danger'  onclick=\"play('" + tableId + "')\">播放" +
            "<span class='glyphicon glyphicon-play'></span></button>" +
            "<button class='btn btn-success' onclick=\"dow('" + tableId + "')\">下载" +
            " <span class='glyphicon glyphicon-arrow-down'></span>" +
            "</button>")
    }

    //播放事件
    function play(tableId) {
        // 判断 用户是否选中一行  未选中->null         选中->被选中行的id
        var gr = $("#" + tableId).jqGrid('getGridParam', 'selrow');
        if (gr == null) {
            alert("请选中要播放的音频");
        } else {
            //1.请求后台
            //2.jqgrid 提供的方法 根据id拿到对应的值
            var data = $("#" + tableId).jqGrid('getRowData', gr);
            var src = data.id;
            $.ajax({
                url: "${prc}/chapter/value",
                data: "id=" + src,
                datatype: "json",
                success: function (data) {
                    var src1 = data.src;
                    $('#myModal').modal('show');
                    $("#myAudio").attr("src", "${prc}/upload/music/" + src1)
                }
            })
        }
    }

    //下载事件
    function dow(tableId) {
        // 判断 用户是否选中一行  未选中->null         选中->被选中行的id
        var gr = $("#" + tableId).jqGrid('getGridParam', 'selrow');
        if (gr == null) {
            alert("请选中要下载的音频");
        } else {
            //1.请求后台
            //2.jqgrid 提供的方法 根据id拿到对应的值
            var data = $("#" + tableId).jqGrid('getRowData', gr);
            var src = data.id;
            $.ajax({
                url: "${prc}/chapter/value",
                data: "id=" + src,
                datatype: "json",
                success: function (data) {
                    var name = data.src
                    location.href = "${prc}/chapter/download?src=" + name;
                }
            })
        }
    }
</script>
<div class="page-header">
    <h2 style="margin-top: -37px;">专辑管理</h2>
</div>


<div class="panel panel-default">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">专辑信息</a></li>
    </ul>
    <div class="panel-footer">
        <div class="alert alert-success" style="display:none" id="msgDiv">
        </div>
        <table id="tab">
        </table>
        <div id="pager" style="height: 50px;"></div>
    </div>
</div>

<div class="modal fade" tabindex="-1" role="dialog" id="myModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">音频播放</h4>
            </div>
            <div class="modal-body">
                <audio autoplay controls src="" id="myAudio"></audio>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->



