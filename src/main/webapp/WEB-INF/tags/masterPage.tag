<%@ tag description="Main Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="sideNav" fragment="true" %>




<!DOCTYPE html>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
<%--    <meta name="_csrf" content="${_csrf.token}"/>--%>
<%--    <meta name="_csrf_header" content="${_csrf.headerName}"/>--%>
<%--    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"--%>
<%--          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">--%>
<%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/datatables.min.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.20/datatables.min.css"/>--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/kokotripadmin.css">

</head>

<body>


<%--<jsp:invoke fragment="header" />--%>


<div class="app-container">

    <t:topNav />

    <div class="content-area">
        <jsp:invoke fragment="sideNav"/>

        <main class="main-container">
            <jsp:doBody/>
        </main>
    </div>
</div>






<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/jquery.spring-friendly.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/kokotripadmin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/jquery-ui.min.js"></script>


</body>

</html>