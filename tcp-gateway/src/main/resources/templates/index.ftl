<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>microavatar framework index</title>
</head>
<body>
    hello microavatar framework index
    <form action="/logout" method="post">
        <!-- 防止CSRF（Cross-site request forgery）跨站请求伪造 -->
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div><input type="submit" value="登出"/></div>
    </form>
</body>
</html>