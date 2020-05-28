<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-03-28
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="${activityVm.name} 상세보기" />

<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>


<t:masterPage>


    <jsp:attribute name="sideNav">
        <t:sideNav activity="true" />
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <t:pathBarDirectory url="/tour-spot/list" name="여행지 리스트" />
                <t:pathBarDirectory url="/tour-spot/detail/${activityVm.tourSpotId}"
                                    name="${activityVm.tourSpotName} 상세보기" />
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${activityVm.name} 상세보기" headingLabel="${activityVm.description}"/>

        </div>


        <div class="section__tab-list">
            <ul class="tab-list">
                <button type="button" class="tab_selected tab-list__tab" data-target-id="tab-basic-info">기본정보</button>
                <button type="button" class="tab_selected tab-list__tab" data-target-id="tab-image">이미지</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-activity-description-list">액티비티설명리스트</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-activity-ticket-list">액티비티 티켓 리스트</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-info-list">번역리스트</button>
            </ul>
        </div>


        <div id="tab-basic-info" class="tab-content">
            <div class="layout__half">
                <div class="section__content border-box">
                    <t:contentWhiteBox label="아이디" value="${activityVm.id.toString()}" />
                    <t:contentWhiteBox label="액티비티명" value="${activityVm.name}" />
                    <t:contentWhiteBox label="요약설명" value="${activityVm.description}"  />
                    <t:contentWhiteBox label="활성화" value="${activityVm.enabled}" />
                    <t:contentWhiteBox label="순서" value="${activityVm.order}" />
                    <t:contentWhiteBox label="위도" value="${activityVm.latitude.toString()}"/>
                    <t:contentWhiteBox label="경도" value="${activityVm.longitude.toString()}"/>
                    <t:contentWhiteBox label="별점" value="${activityVm.averageRate}"/>
                    <t:contentWhiteBox label="별점개수" value="${activityVm.numberOfRate}"/>
                    <t:contentWhiteBox label="유명도" value="${activityVm.popularScore}"/>
                    <t:contentWhiteBox label="위시리스트개수" value="${activityVm.numberOfWishListSaved}"/>
                    <t:contentWhiteBox label="하위분류" value="${activityVm.tagName}"/>
                    <t:contentWhiteBox label="생성일" value="${activityVm.updatedAt}" />
                    <t:contentWhiteBox label="편집일" value="${activityVm.updatedAt}" />
                </div>

                <t:contentFooter id="${activityVm.id}" name="${activityVm.name}"
                                 urlPrefix="/activity" type="액티비티" />

            </div>
        </div>


        <div id="tab-image" class="tab-content">
            <div class="layout__half">
                <t:imageGallery prefixUrl="/activity/image"
                                imageList="${activityVm.baseImageVmList}"
                                ownerIdName="activityVmId"
                                includeRep="true"
                                ownerId="${activityVm.id}"/>
            </div>
        </div>

        <div id="tab-activity-description-list" class="tab-content">
            <t:datatable includeDescription="true"
                         includeOrder="true"
                         nameLabel="머리말"
                         descriptionLabel="본문"
                         dataList="${activityVm.activityDescriptionVmList}"
                         datatableId="activity-description-datatable"
                         urlPrefix="/activity/description"/>
        </div>

        <div id="tab-activity-ticket-list" class="tab-content">
            <t:datatable includeDescription="true"
                         includeOrder="true"
                         nameLabel="액티비티 티켓명"
                         descriptionLabel="요약설명"
                         dataList="${activityVm.activityTicketVmList}"
                         datatableId="activity-ticket-data-table"
                         urlPrefix="/activity/ticket"/>
        </div>

        <div id="tab-info-list" class="tab-content">

            <t:infoTable infoList="${activityVm.activityInfoVmList}"
                         nameLabel="액티비티명"
                         descriptionLabel="요약설명"
                         deleteUrl="/activity/info/delete"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}" />
        </div>

        <jsp:include page="/WEB-INF/views/common/delete-confirm-modal.jsp"/>

        <t:infoFormModal ownerId="${activityVm.id}"
                         ownerFieldName="activityId"
                         ownerTypeLabel="액티비티"
                         nameLabel="액티비티명"
                         descriptionLabel="요약설명"
                         urlPrefix="/activity"
                         includeDescription="true"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}"/>

        <t:infoDetailModal ownerName="${activityVm.name}"
                           nameLabel="액티비티"
                           descriptionLabel="요약설명"
                           includeTag="true"
                           includeDescription="true"/>


    </jsp:body>

</t:masterPage>

<script>
    $(document).ready(function () {
        setImageGallery();
        setDataTable('activity-description-datatable', false, 5, '${pageContext.request.contextPath}',
            '/activity/description', '/add?activityId=${activityVm.id}', true, true);
        setDataTable('activity-ticket-data-table', false, 5, '${pageContext.request.contextPath}',
            '/activity/ticket', '/add?activityId=${activityVm.id}', true, true);
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
