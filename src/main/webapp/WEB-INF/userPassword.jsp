<%--
  Created by IntelliJ IDEA.
  User: peiha
  Date: 2023/4/28
  Time: 11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>银行管理系统 - 修改密码</title>
  <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<header>
  <h1>银行管理系统</h1>
  <nav>
    <ul>
      <li><a href="login?accountType=1">首页</a></li>
      <li><a href="userInfo">个人信息</a></li>
      <li><a href="#">修改密码</a></li>
      <li><a href="userLog">交易记录</a></li>
      <li><a href="login?accountType=3">退出</a></li>
    </ul>
  </nav>
</header>
<main>
  <section class="overview">
    <form method="post" action="/resetUser">
      <h2>修改密码</h2>
      <p>请输入旧密码</p>
      <input type="password" name="oldPassword" placeholder="旧密码"><font color="#dc143c">${passwordMsg1}</font>
      <p>请输入新密码</p>
      <input type="password" name="newPassword" placeholder="新密码">
      <p>请确认新密码</p>
      <input type="password" name="confirmPassword" placeholder="确认新密码"><font color="#dc143c">${passwordMsg2}</font><br>
      <input type="submit" value="修改密码">
      <h2>${passwordMsg}</h2>
    </form>
  </section>

</main>

</body>
</html>