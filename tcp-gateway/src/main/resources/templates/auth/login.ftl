<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>microavatar framework</title>
</head>
<body>
hello login
用户名或密码错ss
您已注销成功
<form id="login-form" action="/" method="post">
    <!-- 防止CSRF（Cross-site request forgery）跨站请求伪造 -->
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <br>
${(param.logout)!}
    <br>
${(param.error)!}
${(SPRING_SECURITY_LAST_EXCEPTION.message)!}

    <div><label> 用户名 : <input id="username" name="username" type="text" value="avatar"/> </label></div>
    <div><label> 密 码 : <input id="password" name="password" type="password" value="123456"/> </label></div>

    <button id="login" type="button">登录</button>
</form>
<script type="text/javascript" src="/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
    'use strict';

    $('#login').on('click', function (e) {
        //用户名
        var $username = $('#username');
        if ($username.val() === '') {
            alert("请输入用户名！");
            return;
        }

        //密码
        var $password = $('#password');
        if ($password.val() === '') {
            alert("请输入密码！");
            return;
        }

        //验证码
//        var $vCode = $('#form-vCode');
//        if ($vCode.val() === '') {
//            $vCode.addClass('input-error');
//            return;
//        } else {
//            $vCode.removeClass('input-error');
//        }

//        var md5Pwd = $.md5($password.val());
//        var md5PwdAccount = $.md5(md5Pwd + $username.val());
//        var md5PwdAccountVCode = $.md5(md5PwdAccount + $vCode.val());
//        $password.val(md5PwdAccountVCode);

        $.ajax({
            url: "/auth/login",
            type: 'post',
            data: {
                username: $username.val(),
                password: $password.val(),
                ${_csrf.parameterName}: '${_csrf.token}'
//                vCode: $vCode.val()
    },
        cache: false,
                dataType:        'json',
                success:        function (resp) {
            console.log("ajax登录请求成功！");
            console.log(resp);

        },
        error: function (text) {
            console.log("ajax登录请求错误！");
            console.log(text);
        }
    })       ;
    });

    $('#login-form').on('keydown', function(e) {
        if (e.keyCode === 13) {
            $('#login').trigger('click');
        }
    });
</script>
</body>
</html>