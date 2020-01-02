<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="prc" value="${pageContext.request.contextPath}"/>


<script>
    $(function () {
        var editor = KindEditor.create("textarea[name='content']", {
            resizeType: 0,
            width: 770,
            allowFileManager: true,
            filePostName: 'img',
            uploadJson: "${prc}/article/upload",
            fileManagerJson: "${prc}/article/getAllImgs",
            afterBlur: function () {
                this.sync();
            }
        })

        $("#tab").jqGrid({
            url: "${prc}/article/page",
            editurl: "${prc}/article/edit",
            pager: "#pager",
            styleUI: "Bootstrap",
            datatype: "json",
            caption: "文章表",
            rowNum: 5,
            rowList: [5, 8, 15],
            viewrecords: true,
            pgbuttons: true,
            width: 1197,
            height: 300,
            colNames: [
                "编号", "标题", "作者", "内容", "上师编号", "创建日期", "状态", //"操作"
            ],
            colModel: [
                {name: "id", align: 'center'},
                {name: "title", align: 'center', editable: true},
                {name: "author", align: 'center', editable: true},
                {name: "content", align: 'center', editable: true},
                {name: "guru_id", align: 'center'},
                {name: "create_date", align: 'center', editable: true, edittype: "date"},
                {name: "status", align: 'center', editable: true},
                /*{
                    name: 'aaa', index: "", formatter: function (value, grid, rows, state) {
                        return "<a class='btn' onclick=\"select()\"><span class='glyphicon glyphicon-list'></span></a>" +
                            "<a class='btn' onclick=\"update()\"><span class='glyphicon glyphicon-pencil'></span></a>"
                    }
                }*/
            ]
        }).jqGrid("navGrid", "#pager", {add: false, edit: false}, {})
    })

    //打开按钮
    $("#add").click(function () {
        $("#mymodel").modal('show');
    })

    //保存关闭
    function closeModle() {
        var serialize = $("#formid").serialize();
        $.ajax({
            url: "${prc}/article/add",
            data: serialize,
            datatype: "json",
            success: function (data) {
                $("#tab").jqGrid().trigger("reloadGrid")
                $("#msg").html(data.msg).show();
                setTimeout(function () {
                    $("#msg").hide()
                }, 5000)
            }
        })
        KindEditor.html("#edit_id", "");
        document.getElementById("formid").reset();
        $("#mymodel").modal('hide');
    }

    //回显单挑数据
    function select() {
        var gr = $("#tab").jqGrid('getGridParam', 'selrow');
        if (gr == null) {
            alert(请选择一条数据)
        } else {
            $("#mytable").empty()
            var data = $("#tab").jqGrid('getRowData', gr);
            var id = data.id;
            $.ajax({
                url: "${prc}/article/ById",
                datatype: "json",
                data: "id=" + id,
                success: function (data) {
                    $("#look").modal('show');
                    $("#divt").html(data.content)
                    /*var tt = $("<tr><td>标题</td><td>" + data.id + "</td></tr>" +
                        "<tr><td>标题</td><td>" + data.title + "</td></tr>" +
                        "<tr><td>作者</td><td>" + data.author + "</td></tr>" +
                        "<tr><td>上师ID</td><td>" + data.guru_id + "</td></tr>" +
                        "<tr><td>创建日期</td><td>" + data.create_date + "</td></tr>" +
                        "<tr><td>状态</td><td>" + data.status + "</td></tr>" +
                        "<tr><td>内容</td><td>" + data.content + "</td></tr>");
                    $("#mytable").append(tt)*/
                }
            })
        }

    }

    //修改数据回显
    function update() {
        var gr = $("#tab").jqGrid('getGridParam', 'selrow');
        if (gr == null) {

        } else {
            $("#myutable").empty()
            var data = $("#tab").jqGrid('getRowData', gr);
            var id = data.id;
            $.ajax({
                url: "${prc}/article/ById",
                datatype: "json",
                data: "id=" + id,
                success: function (data) {
                    $("#ulook").modal('show');
                    var nid = id + "div";

                    var tt = $("<tr><td style='display: none'><input value='" + id + "' name='id' ></td></input></tr>" +
                        "<tr><td>标题</td><td><input value='" + data.title + "' name='title' class='form-control' style='width: 200px'></input></td></tr>" +
                        "<tr><td>作者</td><td><input value='" + data.author + "' name='author' class='form-control' style='width: 200px'></input></td></tr>" +
                        "<tr><td>上师ID</td><td><input value='" + data.guru_id + "' name='guru_id' class='form-control' style='width: 200px'></input></td></tr>" +
                        "<tr><td>创建日期</td><td><input value='" + data.create_date + "' name='create_date' type='date' class='form-control' style='width: 200px'></input></td></tr>" +
                        "<tr><td>状态</td><td><select name='status' id='status' class='form-control' style='width: 200px'>" +
                        "<option value='激活'>激活</option>" +
                        "<option value='未激活'>未激活</option>" +
                        "</select></td></tr>" +
                        "<tr><td>内容</td><td><textarea name='content' id='" + nid + "'>" + data.content + "</textarea></td></tr>");
                    $("#myutable").append(tt)

                    $("#status").val(data.status)

                    var editor = KindEditor.create("#" + nid, {
                        resizeType: 0,
                        width: 770,
                        allowFileManager: true,
                        filePostName: 'img',
                        uploadJson: "${prc}/article/upload",
                        fileManagerJson: "${prc}/article/getAllImgs",
                        afterBlur: function () {
                            this.sync();
                        }
                    })
                }
            })
        }
    }

    //保存修改
    function saveUp() {
        var serialize = $("#update").serialize();
        $.ajax({
            url: "${prc}/article/update",
            data: serialize,
            datatype: "json",
            success: function (data) {
                $("#tab").jqGrid().trigger("reloadGrid")
                $("#msg").html(data.msg).show();
                setTimeout(function () {
                    $("#msg").hide()
                }, 5000)
            }
        })
        document.getElementById("update").reset();
        $("#ulook").modal('hide');
    }


</script>

<div class="page-header">
    <h2 style="margin-top: -37px;">文章管理</h2>
</div>

<div class="alert alert-success" style="display:none" id="msg">

</div>
<div class="panel panel-default">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" role="tab" data-toggle="tab">文章信息</a>
        </li>
        <li role="presentation">
            <a href="javascript:void(0)" id="add">添加文章</a>
        </li>
        <li role="presentation">
            <a class='btn' onclick="select()"><span class='glyphicon glyphicon-list'></span></a>
        </li>

        <li role="presentation">
            <a class='btn' onclick="update()"><span class='glyphicon glyphicon-pencil'></span></a>
        </li>
    </ul>
    <div class="panel-footer">
        <div class="alert alert-success" style="display:none" id="msgDiv">
        </div>
        <table id="tab" class="table table-condensed">
        </table>
        <div id="pager" style="height: 50px;"></div>
    </div>
</div>

<%--添加--%>
<div class="modal fade" role="dialog" id="mymodel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width:800px;height:600px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">文章编写</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="formid">
                    <div class="form-group" id="contentfrom">
                        <label class="col-sm-2 control-label" style="margin-left: -70px">标题</label>
                        <div class="col-sm-4">
                            <input type="email" class="form-control" name="title">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" style="margin-left: -70px">作者</label>
                        <div class="col-sm-4">
                            <input type="email" class="form-control" name="author">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" style="margin-left: -70px">上师ID</label>
                        <div class="col-sm-4">
                            <input type="email" class="form-control" name="guru_id">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" style="margin-left: -70px">状态</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="status">
                                <option>激活</option>
                                <option>未激活</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea class="form-control" id="edit_id" name="content"
                                                 style="width: 90%; height: 230px;">
                            </textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="closeModle()">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

<%--回显--%>
<div class="modal fade" tabindex="-1" role="dialog" id="look">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">文章信息</h4>
            </div>
            <div class="modal-body" id="divt">
               <%-- <table id="mytable" class="table table-bordered table-striped">

                </table>--%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

<%--修改--%>
<div class="modal fade" tabindex="-1" role="dialog" id="ulook">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width:900px;height:610px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">文章信息</h4>
            </div>
            <div class="modal-body" id="divu">
                <form id="update">
                    <table id="myutable" class="table table-bordered table-striped">
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="saveUp()">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
