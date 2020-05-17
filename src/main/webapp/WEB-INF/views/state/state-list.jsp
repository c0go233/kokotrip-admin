<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-01-18
  Time: 21:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <title>시,도 리스트</title>


</head>
<body>


<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav state="true"/>
    </jsp:attribute>

    <jsp:body>


        <div class="path-bar">
            <span>시,도 리스트</span>
        </div>

        <div class="section__heading">
            <h1 class="heading">시,도 리스트</h1>
            <p>대한민국의 행정 구역(大韓民國의 行政 區域)은 대한민국의 통치권을 행사하는 지역에서 1개의 특별시,
                6개의 광역시, 8개의 도, 1개의 특별자치도, 1개의 특별자치시로 구성된다. 이상 총 17개의 행정구역은 광역지방자치단체로 분류된다.</p>
        </div>
        <t:datatable includeDescription="false" includeOrder="false" nameLabel="시,도 명" datatableId="state-data-table" />

    </jsp:body>

</t:masterPage>


<script>
    setDataTable('state-data-table', true, 5, '${pageContext.request.contextPath}', '/state', '/add', false, false);
</script>

</body>
</html>
