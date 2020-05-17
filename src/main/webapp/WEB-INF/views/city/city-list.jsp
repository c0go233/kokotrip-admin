<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-01-26
  Time: 22:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>


<html>
<head>
    <title>도시리스트</title>
</head>
<body>



<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav city="true"/>
    </jsp:attribute>

    <jsp:body>

        <div class="path-bar">
            <span>도시 리스트</span>
        </div>

        <div class="section__heading">
            <h1 class="heading">도시 리스트</h1>
            <p>대한민국의 시(市)는 1949년 8월 15일 19개의 부(府)를 일괄 개칭하여 탄생되었다. 다음의 세 종류로 나눌 수 있다. 광역자치단체인 특별시는 서울특별시 뿐이다.
                광역시는 인천광역시, 대전광역시, 광주광역시, 대구광역시, 부산광역시, 울산광역시이다. 특별자치시는 세종특별자치시 뿐이다. 기초자치단체인 시는 도 산하에 둔다.
                제주특별자치도의 하부 행정 구역으로 지방자치단체가 아닌 행정시는 제주시와 서귀포시이다.</p>
        </div>
        <t:datatable includeDescription="true" includeOrder="false" nameLabel="도시명"
                     descriptionLabel="요약설명" datatableId="city-data-table" />

    </jsp:body>

</t:masterPage>


<script>
    setDataTable('city-data-table', true, 5, '${pageContext.request.contextPath}', '/city', '/add', true, false);
</script>

</body>
</html>
