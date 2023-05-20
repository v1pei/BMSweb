<%--
  Created by IntelliJ IDEA.
  User: peiha
  Date: 2023/4/28
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>银行管理系统 - 登录</title>
  <style>
    body {
      background-color: #f5f5f5;
    }
    .container {
      margin: 100px auto;
      width: 400px;
      background-color: #fff;
      padding: 30px;
      border-radius: 5px;
      box-shadow: 0 0 10px #ccc;
    }
    select{
      font-size: 16px;
      padding: 2px;
      border: none;
      border-radius: 5px;
      box-shadow: inset 0 0 5px #ccc;
      margin-bottom: 5px;
    }
    h1 {
      text-align: center;
      color: #333;
      margin-bottom: 30px;
    }
    label {
      display: block;
      font-size: 16px;
      color: #333;
      margin-bottom: 10px;
    }
    input[type="text"],
    input[type="password"] {
      display: block;
      width: 100%;
      font-size: 16px;
      padding: 10px;
      border: none;
      border-radius: 5px;
      margin-bottom: 20px;
      box-shadow: inset 0 0 5px #ccc;
    }
    button {
      display: block;
      width: 100%;
      background-color: #333;
      color: #fff;
      font-size: 16px;
      padding: 10px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }
    button:hover {
      background-color: #555;
    }
    a{
      text-decoration: none;
      font-size: 16px;
      color: #862727;
    }
    a:hover{
      color: #3c0553;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>银行管理系统 - 登录</h1>
  <form action="login" method="post">
    <label >请选择登录的类型:</label>
    <select name="accountType" size="1">
      <option value="1">用户</option>
      <option value="2">管理员</option>
    </select>
    <label>用户名：</label>
    <input type="text" name="username" placeholder="请输入用户名">
    <label>密码：</label>
    <input type="password" name="password" placeholder="请输入密码">
    <font color="#dc143c">${msg}</font>
    <button type="submit">登录</button>
  </form>
  <a href="forget.jsp" target="_self">忘记密码</a>
</div>
</body>
</html>
