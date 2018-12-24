<%--
  Created by IntelliJ IDEA.
  User: Nihaorz
  Date: 2017/10/11
  Time: 14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>登錄頁面</title>
    <style>
        .login-form {
            width: 200px;
            margin: 0 auto;
            font-size: 14px;
        }

        .login-form p input[type=text], .login-form p input[type=password] {
            width: 200px;
            padding: 5px;
        }

        .login-form p input[type=checkbox], .login-form p label {
            height: 24px;
            margin: 0;
        }

        .login-form p.parent:after {
            content: ' ';
            display: table;
            clear: both;
        }

        .login-form p.message {
            color: red;
        }
    </style>
</head>
<body>
<div style="text-align: center;">
    <c:url value="/login" var="loginUrl"/>
    <form action="${loginUrl}" method="post" class="login-form">
        <p>
            <input type="text" id="username" name="username" placeholder="用户名"/>
        </p>
        <p>
            <input type="password" id="password" name="password" placeholder="密碼"/>
        </p>
        <p class="parent">
            <input type="checkbox" id="keep-login" name="remember-me" checked style="float: left;">
            <label for="keep-login" style="float: left;"> 記住我</label>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" style="float: right;">登錄</button>
        </p>
        <c:if test="${param.error != null}">
            <p class="message">用户名或密碼無效！</p>
        </c:if>
        <c:if test="${param.logout != null}">
            <p class="message">您已註銷！</p>
        </c:if>
    </form>
</div>
</body>
</html>