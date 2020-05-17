<%@ page import="com.kokotripadmin.viewmodel.exception.ExceptionViewModel" %><%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-02-17
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Error</title>
</head>
<body>

<%
    ExceptionViewModel exceptionViewModel = (ExceptionViewModel) request.getAttribute("exceptionViewModel");

%>


<p>status: ${pageContext.response.status}</p>

<p>exception name: ${exceptionViewModel.getExceptionName()}</p>

<p>error message: ${exceptionViewModel.getErrorMessage()}</p>

<p>stack trace:</p>

<c:forEach var="stackTrace" items="${exceptionViewModel.getStackTraceElements()}">
    <p>${stackTrace.toString()}</p>
</c:forEach>
</body>
</html>
