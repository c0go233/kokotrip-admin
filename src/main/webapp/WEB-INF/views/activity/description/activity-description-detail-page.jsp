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

<c:set var="pageTitle" value="${activityDescriptionVm.name} 상세보기" />

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
                <t:pathBarDirectory url="/tour-spot/detail/${activityDescriptionVm.tourSpotId}"
                                    name="${activityDescriptionVm.tourSpotName} 상세보기"/>
                <t:pathBarDirectory url="/activity/detail/${activityDescriptionVm.activityId}"
                                    name="${activityDescriptionVm.activityName} 상세보기"/>
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${activityDescriptionVm.name} 상세보기" headingLabel="${activityDescriptionVm.description}"/>

        </div>


        <div class="section__tab-list">
            <ul class="tab-list">
                <button type="button" class="tab_selected tab-list__tab" data-target-id="tab-basic-info">기본정보</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-info-list">번역리스트</button>
            </ul>
        </div>


        <div id="tab-basic-info" class="tab-content">
            <div class="layout__half">
                <div class="section__content border-box">
                    <t:contentWhiteBox label="아이디" value="${activityDescriptionVm.id.toString()}" />
                    <t:contentWhiteBox label="머리말" value="${activityDescriptionVm.name}" />
                    <t:contentWhiteBox label="본문" value="${activityDescriptionVm.description}"  />
                    <t:contentWhiteBox label="활성화" value="${activityDescriptionVm.enabled}" />
                    <t:contentWhiteBox label="순서" value="${activityDescriptionVm.order}" />
                    <t:contentWhiteBox label="팝업" value="${activityDescriptionVm.popup}" />
                    <t:contentWhiteBox label="생성일" value="${activityDescriptionVm.updatedAt}" />
                    <t:contentWhiteBox label="편집일" value="${activityDescriptionVm.updatedAt}" />
                </div>

                <t:contentFooter id="${activityDescriptionVm.id}" name="${activityDescriptionVm.name}"
                                 urlPrefix="/activity/description" type="액티비티설명" />

            </div>
        </div>


        <div id="tab-info-list" class="tab-content">

            <t:infoTable infoList="${activityDescriptionVm.activityDescriptionInfoVmList}" nameLabel="머리말"
                         descriptionLabel="본문" deleteUrl="/activity/description/info/delete"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}" />
        </div>

        <jsp:include page="/WEB-INF/views/common/delete-confirm-modal.jsp"/>

        <t:infoFormModal ownerId="${activityDescriptionVm.id}" ownerFieldName="activityDescriptionId"
                         ownerTypeLabel="액티비티설명" nameLabel="머리말"
                         descriptionLabel="본문" urlPrefix="/activity/description" includeDescription="true"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}"/>

        <t:infoDetailModal ownerName="${activityDescriptionVm.name}" nameLabel="머리말" descriptionLabel="본문"
                           includeTag="false" includeDescription="true"/>


    </jsp:body>

</t:masterPage>

<script>
    $(document).ready(function () {
        setListenerToTabList();
        setDeleteConfirmModal();
        $('button#delete-btn').on(eventType.click, onClickDeleteBtn);
        setInfoTable(true);
        setInfoFormModal();
        setInfoDetailModal();
    });

</script>


</body>
</html>
