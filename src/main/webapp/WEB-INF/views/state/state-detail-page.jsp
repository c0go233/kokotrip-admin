<%--
  Created by IntelliJ IDEA.
  User: mtae
  Date: 7/04/2020
  Time: 2:26 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ page import="com.kokotripadmin.constant.TabIndex" %>--%>


<html>
<head>
    <title>시,도 상세페이지</title>
</head>
<body>

<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav state="true" />
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <t:pathBarDirectory url="/state/list" name="시,도 리스트" />
                <span>${stateVm.name} 상세보기</span>
            </div>

            <t:sectionHeading heading="${stateVm.name} 상세보기" headingLabel="대한민국의 행정 구역(大韓民國의 行政 區域)은 대한민국의 통치권을 행사하는 지역에서 1개의 특별시,
                    6개의 광역시, 8개의 도, 1개의 특별자치도, 1개의 특별자치시로 구성된다. 이상 총 17개의 행정구역은 광역지방자치단체로 분류된다." />

        </div>


        <div class="section__tab-list">
            <ul class="tab-list">
                <button type="button" class="tab-list__tab" data-target-id="tab-basic-info">기본정보</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-city-list">도시리스트</button>
            </ul>
        </div>


        <div id="tab-basic-info" class="tab-content">
            <div class="layout__half">
                <div class="section__content border-box">
                    <t:contentWhiteBox label="아이디" value="${stateVm.id.toString()}"/>
                    <t:contentWhiteBox label="시,도명" value="${stateVm.name}"/>
                    <t:contentWhiteBox label="활성화" value="${stateVm.enabled}"/>
                    <t:contentWhiteBox label="생성일" value="${stateVm.updatedAt}"/>
                    <t:contentWhiteBox label="편집일" value="${stateVm.updatedAt}"/>
                </div>

                <t:contentFooter id="${stateVm.id}" name="${stateVm.name}" urlPrefix="/state" type="시,도" />
            </div>
        </div>



        <div id="tab-city-list" class="tab-content">
            <t:datatable includeDescription="true" includeOrder="false" nameLabel="도시명"
                         descriptionLabel="요약설명" dataList="${stateVm.cityVmList}" datatableId="city-data-table" urlPrefix="/city"/>
        </div>

        <jsp:include page="/WEB-INF/views/common/delete-confirm-modal.jsp"/>


    </jsp:body>

</t:masterPage>


<script>
    $(document).ready(function () {
        setDataTable('city-data-table', false, 10, '${pageContext.request.contextPath}', '/city', '/add?stateId=${stateVm.id}', true, false);
        setListenerToTabList();
        setDeleteConfirmModal();
        $('button#delete-btn').on(eventType.click, onClickDeleteBtn);
    });
</script>

</body>
</html>
