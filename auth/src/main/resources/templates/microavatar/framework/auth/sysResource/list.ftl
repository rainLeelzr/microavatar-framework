<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>系统资源列表</title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
  <script type="text/javascript" src="/js/miniUI/boot.js"></script>
  <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
  <form id="form1" method="post">
    <div style="width:100%;">
      <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
          <tr>
            <td style="white-space:nowrap;">              <input class="mini-textbox" id="name" name="name" emptyText="请输入名称" style="width:150px;" onenter="onKeyEnter()"/>
              <input class="mini-textbox" id="host" name="host" emptyText="请输入域名或ip" style="width:150px;" onenter="onKeyEnter()"/>              <a class="mini-button" onclick="search()">查询</a>
            </td>
          </tr>
          <tr>
            <td style="width:100%;">
              <a class="mini-button" iconCls="icon-add" onclick="add()">增加</a>
              <a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
              <a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
              <a class="mini-button"  onclick="view()">查看</a>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div class="mini-datagrid" id="datagrid1" style="width:100%;height:400px;" url="/auth/sysResource!query.action" idField="id" allowResize="true" multiSelect="true">
      <div property="columns">
        <div type="checkcolumn" width="30px"></div>        <div field="parentId" headerAlign="center" allowSort="true" width="60px">父id</div>
        
            <div field="name" headerAlign="center" allowSort="true" width="60px">名称</div>
        
            <div field="type" headerAlign="center" allowSort="true" width="60px">类型</div>
        
            <div field="host" headerAlign="center" allowSort="true" width="60px">域名或ip</div>
        
            <div field="url" headerAlign="center" allowSort="true" width="60px">路径</div>
        
            <div field="orderNum" headerAlign="center" allowSort="true" width="60px">顺序</div>
        
            <div field="enabled" headerAlign="center" allowSort="true" width="60px">启用</div>
        
            <div field="remark" headerAlign="center" allowSort="true" width="60px">备注</div>
        
          </div>
    </div>
  </form>

  <script type="text/javascript">
    mini.parse();

    var grid = mini.get("datagrid1");
    grid.load();
      
    /*
    *功能：新增
    */
    function add() {
      mini.open({
        url: "/auth/sysResource!edit.action",
        title: "新增系统资源",
        width: 1000,
        height: 600,
        onload: function() {
          var iframe = this.getIFrameEl();
          var data = {
            action: "new"
          };
          iframe.contentWindow.SetData(data);
        },
        ondestroy: function(action) {
          grid.reload();
        }
      });
    }
        
    /**
    *功能：编辑
    */
    function edit() {
      var row = grid.getSelected();
      if (row) {
        mini.open({
          url: "/auth/sysResource!edit.action",
          title: "编辑系统资源",
          width: 1000,
          height: 600,
          onload: function() {
            var iframe = this.getIFrameEl();
            var data = {
              action: "edit",
              id: row.id
            };
            iframe.contentWindow.SetData(data);
          },
          ondestroy: function(action) {
            grid.reload();
          }
        });
      } else {
        alert("请选中一条记录");
      }
    }

    /**
    *功能：删除选中的记录
    */
    function remove() {
      var rows = grid.getSelecteds();
      if (rows.length > 0) {
        if (confirm("确定删除选中记录？")) {
          var ids = [];
          for (var i = 0, l = rows.length; i < l; i++) {
            var r = rows[i];
            ids.push(r.id);
          }
          var id = ids.join(',');
          grid.loading("操作中，请稍后......");
          $.ajax({
            url: "/auth/sysResource!delete.action",
            type: 'post',
            data: {
              id: id
            },
            cache: false,
              success: function (text) {
                alert("成功删除!")
                grid.reload();
              },
              error: function (text) {
                alert("删除失败!");
              }
          });
        }
      } else {
        alert("请选中一条记录");
      }
    }
        
    /**
     *功能：查看
     */
    function view() {
      var row = grid.getSelected();
      if (row) {
        mini.open({
          url: "/auth/sysResource!edit.action",
          title: "查看系统资源",
          width: 1000,
          height: 600,
          onload: function() {
            var iframe = this.getIFrameEl();
            var data = {
              action: "view",
              id: row.id
            };
            iframe.contentWindow.SetData(data);
          },
          ondestroy: function(action) {
            grid.reload();
          }
        });
      } else {
        alert("请选中一条记录");
      }
    }
        
    function search() {
      var form = new mini.Form("form1");
      var o = form.getData(); 
       grid.load(o);
    }

    function onKeyEnter(e) {
      search();
    }
  </script>
  </body>
</html>