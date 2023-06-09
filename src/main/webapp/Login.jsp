<%--
  Created by IntelliJ IDEA.
  User: SakuraKoujin
  Date: 2023/3/27
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<h1>登录</h1>
<form action="LoginServlet" method="post">
    <label>
        姓名
        <input type="text" name="name">
    </label>
    <br><br>
    <label>
        密码
        <input type="password" name="pwd">
    </label>
    <input type="hidden" name="action" value="login">
    <br><br>
    <input type="submit">

</form>
<a href="signUp.jsp">注册账号</a>
</body>
</html>
