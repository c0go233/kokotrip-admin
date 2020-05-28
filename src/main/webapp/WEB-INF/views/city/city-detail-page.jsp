<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-02-09
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle" value="${cityVm.name} 상세보기" />

<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>


<t:masterPage>


    <jsp:attribute name="sideNav">
        <t:sideNav city="true" />
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <t:pathBarDirectory url="/state/list" name="시,도 리스트" />
                <t:pathBarDirectory url="/state/detail/${cityVm.stateId}" name="${cityVm.stateName} 상세보기" />
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${cityVm.name} 상세보기" headingLabel="${cityVm.description}"/>

        </div>


        <div class="section__tab-list">
            <ul class="tab-list">
                <button type="button" class="tab_selected tab-list__tab" data-target-id="tab-basic-info">기본정보</button>
                <button type="button" class="tab_selected tab-list__tab" data-target-id="tab-image">이미지</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-region-list">유명지역 리스트</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-info-list">번역리스트</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-tag-list">분류리스트</button>
            </ul>
        </div>


        <div id="tab-basic-info" class="tab-content">
            <div class="layout__half">
                <div class="section__content border-box">
                    <t:contentWhiteBox label="아이디" value="${cityVm.id.toString()}" />
                    <t:contentWhiteBox label="도시명" value="${cityVm.name}" />
                    <t:contentWhiteBox label="요약설명" value="${cityVm.description}"  />
                    <t:contentWhiteBox label="활성화" value="${cityVm.enabled}" />
                    <t:contentWhiteBox label="위도" value="${cityVm.latitude.toString()}" />
                    <t:contentWhiteBox label="경도" value="${cityVm.longitude.toString()}" />
                    <t:contentWhiteBox label="생성일" value="${cityVm.updatedAt}" />
                    <t:contentWhiteBox label="편집일" value="${cityVm.updatedAt}" />
                </div>

                <t:contentFooter id="${cityVm.id}" name="${cityVm.name}" urlPrefix="/city" type="도시" />

            </div>
        </div>

        <div id="tab-image" class="tab-content">
            <div class="layout__half">
                <t:imageGallery prefixUrl="/city/image"
                                imageList="${cityVm.baseImageVmList}"
                                ownerIdName="cityId"
                                includeRep="true"
                                ownerId="${cityVm.id}"/>
            </div>
        </div>



        <div id="tab-region-list" class="tab-content">
            <t:datatable includeDescription="true" includeOrder="false" nameLabel="유명지역명" descriptionLabel="요약설명"
                         dataList="${cityVm.regionVmList}" datatableId="region-data-table" urlPrefix="/region"/>
        </div>





        <div id="tab-tag-list" class="tab-content">
            <div class="layout__half">
                <div class="section__content border-box">

                    <c:forEach var="themeRel" items="${cityVm.themeRelVmList}">
                        <t:contentWhiteBox label="${themeRel.themeName}" value="${themeRel.numOfAllTag.toString()}"
                                           classForWhiteBox="content__theme-white-box" />
                        <c:forEach var="themeTagRel" items="${themeRel.themeTagRelVmList}">
                            <t:contentWhiteBox label="${themeTagRel.tagName}" value="${themeTagRel.numOfTag.toString()}"
                                            classForLabelWrapper="white-box__tag-label-wrapper" />
                        </c:forEach>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div id="tab-info-list" class="tab-content">

            <t:infoTable infoList="${cityVm.cityInfoVmList}" nameLabel="도시명"
                         descriptionLabel="요약설명" deleteUrl="/city/info/delete"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}" />
        </div>

        <jsp:include page="/WEB-INF/views/common/delete-confirm-modal.jsp"/>

        <t:infoFormModal ownerId="${cityVm.id}" ownerFieldName="cityId" ownerTypeLabel="도시" nameLabel="도시명"
                         descriptionLabel="요약설명" urlPrefix="/city" includeDescription="true"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}"/>

        <t:infoDetailModal ownerName="${cityVm.name}" nameLabel="도시명" descriptionLabel="요약설명"
                           includeTag="false" includeDescription="true"/>


    </jsp:body>

</t:masterPage>

<script>
    $(document).ready(function () {
        setImageGallery();

        setDataTable('region-data-table', false, 5, '${pageContext.request.contextPath}', '/region', '/add?cityId=${cityVm.id}', true, false);
        setListenerToTabList();
        setDeleteConfirmModal();
        $('button#delete-btn').on(eventType.click, onClickDetailDeleteBtn);
        setInfoTable(true);
        setInfoFormModal();
        setInfoDetailModal();
    });



</script>


</body>
</html>
