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
            <td class="tdtitle">账号：</td>
            <td class="tdcontent">              <input class="mini-textbox" id="account" name="account" emptyText="请输入账号" vtype="maxLength:16" required="true" .if(false)onvalidation="onUniqueValidate".endif/>            </td>
          </tr>          <tr>
            <td class="tdtitle">密码：</td>
            <td class="tdcontent">              <input class="mini-textbox" id="pwd" name="pwd" emptyText="请输入密码" vtype="maxLength:32" required="true" .if(false)onvalidation="onUniqueValidate".endif/>            </td>
          </tr>          <tr>
            <td class="tdtitle">创建时间：</td>
            <td class="tdcontent">              <input class="mini-textbox" id="createTime" name="createTime" emptyText="请输入创建时间" vtype="maxLength:0" required="true" .if(false)onvalidation="onUniqueValidate".endif/>            </td>
          </tr>          <tr>
            <td class="tdtitle">名称：</td>
            <td class="tdcontent">              <input class="mini-textbox" id="name" name="name" emptyText="请输入名称" vtype="maxLength:16" required="false" .if(false)onvalidation="onUniqueValidate".endif/>            </td>
          </tr>          <tr>
            <td class="tdtitle">状态：</td>
            <td class="tdcontent">              <input class="mini-textbox" id="status" name="status" emptyText="请输入状态" vtype="maxLength:0" required="false" .if(false)onvalidation="onUniqueValidate".endif/>            </td>
          </tr>        </table>
      </div>      <div id="btnControl" style="text-align:center;padding:10px;">
        <a class="mini-button" style="width:60px;margin-right:20px;" onclick="onOk">确定</a>
        <a class="mini-button" style="width:60px;" onclick="onCancel">取消</a>
      </div>
    </form>
    <script type="text/javascript">
      mini.parse();

      var form = new mini.Form("form1");
      /**
       * 保存
       */
      function SaveData() {
        form.validate();
        if (form.isValid() == false) return;

        var o = form.getData();           var json = mini.encode(o);
        $.ajax({
          url: "/auth/user!save.action",
          type: 'post',
          data: { 
            data: json           },
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
        var data = mini.clone(data);        if (data.action == "edit" || data.action == "view") {
          $.ajax({
            url: "/auth/user!get.action",
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
                  if (c.addCls) c.addCls("asLabel");          //增加asLabel外观                }
              }
            }
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

      //唯一性校验
      function onUniqueValidate(e){
        if (e.isValid) {
          $.ajax({
            url:"/auth/user!validateUnique.action",
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