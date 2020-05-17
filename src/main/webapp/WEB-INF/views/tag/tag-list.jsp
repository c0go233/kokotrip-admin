<%--
  Created by IntelliJ IDEA.
  User: mtae
  Date: 17/04/2020
  Time: 2:08 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle" value="하위분류 리스트"/>

<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>


<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav tag="true"/>
    </jsp:attribute>

    <jsp:body>


        <div class="path-bar">
            <span>${pageTitle}</span>
        </div>

        <div class="section__heading">
            <h1 class="heading">${pageTitle}</h1>
            <p>분류는 주제를 나누기 위한 일종의 목록이며, 사용자들이 정보를 쉽게 찾을 수 있도록 도와줍니다.
                예를 들어 상위분류:문화에는 하위분류:뮤지컬, 연극, 영화등이 있습니다.</p>
        </div>

        <t:datatable includeDescription="false" includeOrder="false" nameLabel="하위분류명" datatableId="tag-data-table" />

    </jsp:body>

</t:masterPage>


<script>
    setDataTable('tag-data-table', true, 5, '${pageContext.request.contextPath}', '/tag', '/add', false, false);
</script>


</body>
</html>