<%--
  Created by IntelliJ IDEA.
  User: mtae
  Date: 4/02/2020
  Time: 3:15 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>


<html>
<head>
    <title>Title</title>
</head>
<body>



<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav region="true"/>
    </jsp:attribute>

    <jsp:body>


        <div class="path-bar">
            <span>유명지역 리스트</span>
        </div>

        <div class="section__heading">
            <h1 class="heading">유명지역</h1>
            <p>그냥 동이라고 하면 행정동을 말한다. 법정동과 구별하고 있으며, 원칙적으로는 모든 행정동에 동주민센터가 있다.
                그러나 재건축이나 재개발에 의해 주민이 없거나 아예 없어지면 일시적으로 이웃한 동주민센터에서 업무를 보기도 한다.</p>
        </div>

        <t:datatable includeDescription="true" includeOrder="false" nameLabel="유명지역명" descriptionLabel="요약설명" datatableId="region-data-table" />
    </jsp:body>

</t:masterPage>


<script>
    setDataTable('region-data-table', true, 5, '${pageContext.request.contextPath}', '/region', '/add', true, false);
</script>


</body>
</html>
