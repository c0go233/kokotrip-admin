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

<c:set var="pageTitle" value="${photoZoneVm.name} 상세보기" />

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
                <t:pathBarDirectory url="/tour-spot/detail/${photoZoneVm.parentTourSpotId}"
                                    name="${photoZoneVm.parentTourSpotName} 상세보기" />
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${photoZoneVm.name} 상세보기" headingLabel="${photoZoneVm.description}"/>

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
                    <t:contentWhiteBox label="아이디" value="${photoZoneVm.id.toString()}" />
                    <t:contentWhiteBox label="머리말" value="${photoZoneVm.name}" />
                    <t:contentWhiteBox label="본문" value="${photoZoneVm.description}"  />
                    <t:contentWhiteBox label="활성화" value="${photoZoneVm.enabled}" />
                    <t:contentWhiteBox label="순서" value="${photoZoneVm.order}" />
                    <t:contentWhiteBox label="위도" value="${photoZoneVm.latitude}"/>
                    <t:contentWhiteBox label="경도" value="${photoZoneVm.longitude}"/>
                    <t:contentWhiteBox label="연계여행지" value="${photoZoneVm.tourSpotName}"/>
                    <t:contentWhiteBox label="연계액티비티" value="${photoZoneVm.activityName}"/>
                    <t:contentWhiteBox label="생성일" value="${photoZoneVm.updatedAt}" />
                    <t:contentWhiteBox label="편집일" value="${photoZoneVm.updatedAt}" />
                </div>

                <t:contentFooter id="${photoZoneVm.id}" name="${photoZoneVm.name}"
                                 urlPrefix="/photo-zone" type="포토존" />

            </div>
        </div>


        <div id="tab-info-list" class="tab-content">

            <t:infoTable infoList="${photoZoneVm.photoZoneInfoVmList}" nameLabel="포토존명"
                         descriptionLabel="요약설명" deleteUrl="/photo-zone/info/delete"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}" />
        </div>

        <jsp:include page="/WEB-INF/views/common/delete-confirm-modal.jsp"/>

        <t:infoFormModal ownerId="${photoZoneVm.id}" ownerFieldName="photoZoneId" ownerTypeLabel="포토존" nameLabel="포토존명"
                         descriptionLabel="요약설명" urlPrefix="/photo-zone" includeDescription="true"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}"/>

        <t:infoDetailModal ownerName="${photoZoneVm.name}" nameLabel="포토존명" descriptionLabel="요약설명"
                           includeTag="false" includeDescription="true"/>


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
