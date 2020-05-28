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

<c:set var="pageTitle" value="${activityTicketDescriptionVm.name} 상세보기" />

<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>


<t:masterPage>


    <jsp:attribute name="sideNav">
        <t:sideNav tourSpot="true" />
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <t:pathBarDirectory url="/tour-spot/list" name="여행지 리스트" />
                <t:pathBarDirectory url="/tour-spot/detail/${activityTicketDescriptionVm.tourSpotId}"
                                    name="${activityTicketDescriptionVm.tourSpotName} 상세보기" />
                <t:pathBarDirectory url="/activity/detail/${activityTicketDescriptionVm.activityId}"
                                    name="${activityTicketDescriptionVm.activityName} 상세보기"/>
                <t:pathBarDirectory url="/activity/ticket/detail${activityTicketDescriptionVm.activityTicketId}"
                                    name="${activityTicketDescriptionVm.activityTicketName} 상세보기"/>
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${activityTicketDescriptionVm.name} 상세보기"
                              headingLabel="${activityTicketDescriptionVm.description}"/>

        </div>


        <div class="section__tab-list">
            <ul class="tab-list">
                <button type="button" class="tab_selected tab-list__tab" data-target-id="tab-basic-info">기본정보</button>
                <button type="button" class="tab_selected tab-list__tab" data-target-id="tab-image">이미지</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-info-list">번역리스트</button>
            </ul>
        </div>


        <div id="tab-basic-info" class="tab-content">
            <div class="layout__half">
                <div class="section__content border-box">
                    <t:contentWhiteBox label="아이디" value="${activityTicketDescriptionVm.id.toString()}" />
                    <t:contentWhiteBox label="머리말" value="${activityTicketDescriptionVm.name}" />
                    <t:contentWhiteBox label="본문" value="${activityTicketDescriptionVm.description}"  />
                    <t:contentWhiteBox label="활성화" value="${activityTicketDescriptionVm.enabled}" />
                    <t:contentWhiteBox label="순서" value="${activityTicketDescriptionVm.order}" />
                    <t:contentWhiteBox label="팝업" value="${activityTicketDescriptionVm.popup}" />
                    <t:contentWhiteBox label="생성일" value="${activityTicketDescriptionVm.updatedAt}" />
                    <t:contentWhiteBox label="편집일" value="${activityTicketDescriptionVm.updatedAt}" />
                </div>

                <t:contentFooter id="${activityTicketDescriptionVm.id}" name="${activityTicketDescriptionVm.name}"
                                 urlPrefix="/activity/ticket/description" type="액티비티티켓설명" />

            </div>
        </div>


        <div id="tab-image" class="tab-content">
            <div class="layout__half">
                <t:imageGallery prefixUrl="/activity/ticket/description/image"
                                imageList="${activityTicketDescriptionVm.baseImageVmList}"
                                ownerIdName="activityTicketDescriptionVmId"
                                includeRep="false"
                                ownerId="${activityTicketDescriptionVm.id}"/>
            </div>
        </div>

        <div id="tab-info-list" class="tab-content">

            <t:infoTable infoList="${activityTicketDescriptionVm.activityTicketDescriptionInfoVmList}"
                         nameLabel="머리말"
                         descriptionLabel="본문"
                         deleteUrl="/activity/ticket/description/info/delete"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}" />
        </div>

        <jsp:include page="/WEB-INF/views/common/delete-confirm-modal.jsp"/>

        <t:infoFormModal ownerId="${activityTicketDescriptionVm.id}"
                         ownerFieldName="activityTicketDescriptionId"
                         ownerTypeLabel="액티비티티켓설명"
                         nameLabel="머리말"
                         descriptionLabel="본문"
                         urlPrefix="/activity/ticket/description"
                         includeDescription="true"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}"/>

        <t:infoDetailModal ownerName="${activityTicketDescriptionVm.name}"
                           nameLabel="머리말"
                           descriptionLabel="본문"
                           includeTag="false"
                           includeDescription="true"/>


    </jsp:body>

</t:masterPage>

<script>
    $(document).ready(function () {
        setImageGallery();
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
