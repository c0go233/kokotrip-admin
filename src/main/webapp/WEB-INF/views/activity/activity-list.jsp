<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-02-16
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle" value="액티비티 리스트"/>

<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>

<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav activity="true"/>
    </jsp:attribute>

    <jsp:body>


        <div class="path-bar">
            <span>${pageTitle}</span>
        </div>

        <div class="section__heading">
            <h1 class="heading">${pageTitle}</h1>
            <p>우리나라에 엄청난 관광객들이 몰려오고 있는 글로벌한 시대이다. 과거에는 부산이나 서울을 제외하고는 외국인 관광객들이 거의 관심을 두지 않았는데
                시대가 바뀌고 지방의 도시들도 좋은 곳들이 많아짐에 따라 전국에 외국인 관광객의 발길이 닿지 않는 곳이 없을 정도다.</p>
        </div>

        <table id="activity-data-table" class="k-table border-box">
            <thead>
            <tr>
                <th><span>아이디</span><div class="diamond"></div></th>
                <th><span>액티비티명</span><div class="diamond"></div></th>
                <th><span>요약설명</span><div class="diamond"></div></th>
                <th><span>하위분류명</span><div class="diamond"></div></th>
                <th><span>활성화</span><div class="diamond"></div></th>
                <th><span>여행지명</span><div class="diamond"></div></th>
                <th>편집</th>
                <th>상세</th>
            </tr>
            </thead>
        </table>

    </jsp:body>

</t:masterPage>


<script>
    setActivityDataTable('${pageContext.request.contextPath}', '/activity');
    <%--setDataTable('activity-data-table', true, 5, '${pageContext.request.contextPath}', '/activity', '/add', true, false);--%>
</script>


</body>
</html>
