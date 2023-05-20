<%--
  Created by IntelliJ IDEA.
  User: peiha
  Date: 2023/4/28
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h2>当前用户：${user.getName()}</h2>
    <h3>日志信息</h3>
    ${logMsg}
      <c:forEach var="logInfo" items="${sessionScope.userLog}" >
        <c:out value="${logInfo}"/><br>
      </c:forEach>

  </section>

</main>

</body>
</html>