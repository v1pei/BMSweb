<%--
  Created by IntelliJ IDEA.
  User: peiha
  Date: 2023/4/28
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>银行管理系统 - 用户信息</title>
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
    <h2>当前为：普通用户</h2>
    <table>
      <tr>
        <th>用户名</th>
        <th>电话</th>
        <th>邮箱</th>
        <th>身份证号</th>
      </tr>
      <tr>
        <td>${user.getName()}</td>
        <td>${user.getPhone()}</td>
        <td>${user.getMail()}</td>
        <td>${user.getIdCard()}</td>
      </tr>
    </table>
  </section>

</main>

</body>
</html>
