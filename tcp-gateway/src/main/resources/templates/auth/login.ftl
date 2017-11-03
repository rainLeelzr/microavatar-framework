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
<form action="/login" method="post">
    <br>
${(param.logout)!}
    <br>
${(param.error)!}
${(SPRING_SECURITY_LAST_EXCEPTION.message)!}

    <div><label> 用户名 : <input type="text" name="username"/> </label></div>
    <div><label> 密 码 : <input type="password" name="password"/> </label></div>

    <!-- 防止CSRF（Cross-site request forgery）跨站请求伪造 -->
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div><input type="submit" value="登录"/></div>
</form>
</body>
</html>