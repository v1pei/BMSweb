<%--
  Created by IntelliJ IDEA.
  User: peiha
  Date: 2023/4/28
  Time: 14:27
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
        <h2>当前登录用户为：${user.getName()}</h2>
        <h3>当前账户余额：${user.getMoney()}</h3>
        <form action="usersave" method="post">
        请输入要存入的余额:<input type="number" name="saveMoney"><br>
            <h3>${saveMsg}</h3>
            <input type="submit" value="存入">
        </form>
    </section>

</main>

</body>
</html>
