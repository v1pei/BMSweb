<%--
  Created by IntelliJ IDEA.
  User: peiha
  Date: 2023/4/28
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>银行管理系统 - 用户主页面</title>
  <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<header>
  <h1>银行管理系统</h1>
  <nav>
    <ul>
      <li><a href="login?accountType=1">首页</a></li>
      <li><a href="userInfo">个人信息</a></li>
      <li><a href="resetUser">修改密码</a></li>
      <li><a href="userLog">交易记录</a></li>
      <li><a href="login?accountType=3">退出</a></li>
    </ul>
  </nav>
</header>
<main>
  <section class="overview">
    <h2>欢迎您!${user.getName()}</h2>
    <table>
      <tr>
        <th>账户名称</th>
        <th>余额</th>
        <th>操作</th>
      </tr>
      <tr>
        <td>存款</td>
        <td>${user.getMoney()}</td>
        <td><a href="usersave">存款</a> / <a href="userGet">取款</a></td>
      </tr>
    </table>
  </section>

</main>

</body>
</html>
