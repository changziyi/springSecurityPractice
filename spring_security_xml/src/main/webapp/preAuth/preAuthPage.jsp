<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
</head>
<body>
	preAuthPage
	<c:url value="/logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="submit" value="退出" />
	</form>
</body>
</html>