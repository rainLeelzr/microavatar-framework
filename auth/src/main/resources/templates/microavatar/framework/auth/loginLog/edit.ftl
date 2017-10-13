<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>详细页面</title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <script type="text/javascript" src="/js/miniUI/boot.js"></script>
  <link rel="icon" href="favicon.ico" type="image/x-icon">
  <style type="text/css">
    html, body{
      font-size:12px;
      padding:0;
      margin:0;
      border:0;
      height:100%;
    }
  </style>
</head>
<body>    
    <form id="form1" method="post">
      <div style="padding-left:11px;padding-bottom:5px;">        <input name="id" id="id" class="mini-hidden" required="false"/>    
        
        <table style="table-layout:fixed;" class="table2">          <tr>
            <td class="tdtitle">用户主键：</td>
            <td class="tdcontent">              <input class="mini-textbox" id="userId" name="userId" emptyText="请输入用户主键" vtype="maxLength:36" required="true" .if(false)onvalidation="onUniqueValidate".endif/>            </td>
          </tr>          <tr>
            <td class="tdtitle">登录方式：</td>
            <td class="tdcontent">              <input class="mini-textbox" id="loginType" name="loginType" emptyText="请输入登录方式" vtype="maxLength:0" required="true" .if(false)onvalidation="onUniqueValidate".endif/>            </td>
          </tr>          <tr>
            <td class="tdtitle">客户端登录ip：</td>
            <td class="tdcontent">              <input class="mini-textbox" id="clientIp" name="clientIp" emptyText="请输入客户端登录ip" vtype="maxLength:16" required="false" .if(false)onvalidation="onUniqueValidate".endif/>            </td>
          </tr>          <tr>
            <td class="tdtitle">登录时间：</td>
            <td class="tdcontent">              <input class="mini-textbox" id="loginTime" name="loginTime" emptyText="请输入登录时间" vtype="maxLength:0" required="true" .if(false)onvalidation="onUniqueValidate".endif/>            </td>
          </tr>          <tr>
            <td class="tdtitle">登出时间：</td>
            <td class="tdcontent">              <input class="mini-textbox" id="logoutTime" name="logoutTime" emptyText="请输入登出时间" vtype="maxLength:0" required="false" .if(false)onvalidation="onUniqueValidate".endif/>            </td>
          </tr>          <tr>
            <td class="tdtitle">状态：</td>
            <td class="tdcontent">              <input class="mini-textbox" id="status" name="status" emptyText="请输入状态" vtype="maxLength:0" required="false" .if(false)onvalidation="onUniqueValidate".endif/>            </td>
          </tr>        </table>
      </div>      <fieldset style="width: 700px; border: solid 1px #aaa; margin:0 auto;">
        <legend>附件信息</legend>
        <div class="mini-datagrid" id="datagrid1" style="width:100%; height:100px;" url="/base/attachment!query.action" idField="id" multiSelect="true" allowResize="true">
          <div property="columns">
            <div field="fileName" width="80%" headerAlign="center" allowSort="false">文件名称</div>
            <div name="action" width="20%" headerAlign="center" align="center" cellStyle="padding:0;" renderer="onActionRenderer">操作</div>
          </div>
        </div>
        <div id="newAttachments">
          <span id="newAttachBtn">
            新增加附件
            <a href="javascript:openUpload()">上传附件</a>
          </span>
        </div>
      </fieldset>      <div id="btnControl" style="text-align:center;padding:10px;">
        <a class="mini-button" style="width:60px;margin-right:20px;" onclick="onOk">确定</a>
        <a class="mini-button" style="width:60px;" onclick="onCancel">取消</a>
      </div>
    </form>
    <script type="text/javascript">
      mini.parse();

      var form = new mini.Form("form1");      var attacheIds="";
      var optAction="";
      /**
       * 保存
       */
      function SaveData() {
        form.validate();
        if (form.isValid() == false) return;

        var o = form.getData();           var json = mini.encode(o);
        $.ajax({
          url: "/auth/loginLog!save.action",
          type: 'post',
          data: { 
            data: json             ,fileIds:attacheIds          },
          cache: false,
          success: function (text) {
            CloseWindow("save");
          },
          error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
            CloseWindow();
          }
        });
      }

        ////////////////////
        //标准方法接口定义
      function SetData(data) {
        //跨页面传递的数据对象，克隆后才可以安全使用
        var data = mini.clone(data);        optAction=data.action;
        if (data.action == "edit" || data.action == "view") {
          $.ajax({
            url: "/auth/loginLog!get.action",
            type:"post",
            data:{
              id:data.id
            },
            cache: false,
            success: function (text) {
              var o = mini.decode(text);
              form.setData(o);
              form.setChanged(false);

              if(data.action=="view"){
                var fields = form.getFields();   
                $("#btnControl").hide();             
                for (var i = 0, l = fields.length; i < l; i++) {
                  var c = fields[i];
                  if (c.setReadOnly) c.setReadOnly(true);     //只读
                  if (c.setIsValid) c.setIsValid(true);      //去除错误提示
                  if (c.addCls) c.addCls("asLabel");          //增加asLabel外观                  $("#newAttachments").hide();                }
              }
            }
          });          //加载附件
          var grid = mini.get("datagrid1");       
          grid.load({
            bizId:data.id
          });        }
      }

      function GetData() {
        var o = form.getData();
        return o;
      }

      function CloseWindow(action) {            
        if (action == "close" && form.isChanged()) {
          if (confirm("数据被修改了，是否先保存？")) {
            return false;
          }
        }
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();            
      }

      function onOk(e) {
        SaveData();
      }

      function onCancel(e) {
        CloseWindow("cancel");
      }
      function openUpload(){
        mini.open({
          url : "/base/attachment!uploadPage.action?bizCode=loginLog",
          title : "上传附件",
          width : 1000,
          height : 600,
          onload : function() {
            var iframe = this.getIFrameEl();
            var data = {
              action : "new"
            };
          },
          ondestroy : function(action) {
            var grid = mini.get("datagrid1");
            var iframe = this.getIFrameEl();
            var data = iframe.contentWindow.GetData();
            data = mini.clone(data);    //必须
            if (data) {
              attacheIds=data.value;
              var files=data.name.split(",");
              for(var i=0;i<files.length;i++){
                $('<li></li>').appendTo('#newAttachments').text(files[i]);
              }
            }
          }
        });
      }
    
      function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;
        var rowIndex = e.rowIndex;
        var s="";
        if(optAction=="view"){
           s = '<a target="newwindow" class="" href="/base/file!download.action?filePath=' + record.path + '&fileName=' + encodeURI(record.fileName)
            + '">下载</a>';
        }else{
           s = '<a class="New_Button" href="javascript:deleteAttach(\''
            + record.id + '\')">删除</a>'+'&nbsp;&nbsp;<a target="newwindow" class="" href="/base/file!download.action?filePath=' + record.path + '&fileName=' + encodeURI(record.fileName)
            + '">下载</a>';
        }
        return s;
      }
    
      function deleteAttach(id){
        if (confirm("确定删除该附件？")) {
          var grid = mini.get("datagrid1");
          grid.loading("操作中，请稍后......");
          $.ajax({
            url : "/base/attachment!delete.action",
            type : 'post',
            data : {
              id : id
            },
            cache : false,
            success : function(text) {
              alert("成功删除!")
              grid.reload();
            },
            error : function(text) {
              alert("删除失败!");
            }
          });
        }
      }
      //唯一性校验
      function onUniqueValidate(e){
        if (e.isValid) {
          $.ajax({
            url:"/auth/loginLog!validateUnique.action",
            type:"post",
            data: {
              data: e.sender.name + "~" + e.value,
              id: mini.get("id").value
            },
            cache:false,
            success: function(text) {
              if(text=="true"){
                alert( "该____已经存在，请重新输入");
                $(e.sender).focus();
              }
            },
            error: function(jqXHR, textStatus, errorThrown) {
              alert("校验___唯一性失败!");
            }
          });  
        }
      }
    </script>
  </body>
</html>