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




<%--<jsp:invoke fragment="footer" />--%>

<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"--%>
<%--        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"--%>
<%--        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"--%>
<%--        crossorigin="anonymous"></script>--%>


<%--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/jquery.dataTables.min.js"></script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/jquery.spring-friendly.js"></script>
<%--<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.20/datatables.min.js"></script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/kokotripadmin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/jquery-ui.min.js"></script>

<%--<script src="https://kit.fontawesome.com/288de09b2e.js" crossorigin="anonymous"></script>--%>


</body>

</html>