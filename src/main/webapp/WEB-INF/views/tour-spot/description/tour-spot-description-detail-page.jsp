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

<c:set var="pageTitle" value="${tourSpotDescriptionVm.name} 상세보기" />

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
                <t:pathBarDirectory url="/tour-spot/detail/${tourSpotDescriptionVm.tourSpotId}"
                                    name="${tourSpotDescriptionVm.tourSpotName} 상세보기" />
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${tourSpotDescriptionVm.name} 상세보기" headingLabel="${tourSpotDescriptionVm.description}"/>

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
                    <t:contentWhiteBox label="아이디" value="${tourSpotDescriptionVm.id.toString()}" />
                    <t:contentWhiteBox label="머리말" value="${tourSpotDescriptionVm.name}" />
                    <t:contentWhiteBox label="본문" value="${tourSpotDescriptionVm.description}"  />
                    <t:contentWhiteBox label="활성화" value="${tourSpotDescriptionVm.enabled}" />
                    <t:contentWhiteBox label="순서" value="${tourSpotDescriptionVm.order}" />
                    <t:contentWhiteBox label="팝업" value="${tourSpotDescriptionVm.popup}" />
                    <t:contentWhiteBox label="생성일" value="${tourSpotDescriptionVm.updatedAt}" />
                    <t:contentWhiteBox label="편집일" value="${tourSpotDescriptionVm.updatedAt}" />
                </div>

                <t:contentFooter id="${tourSpotDescriptionVm.id}" name="${tourSpotDescriptionVm.name}"
                                 urlPrefix="/tour-spot/description" type="여행지설명" />

            </div>
        </div>


        <div id="tab-info-list" class="tab-content">

            <t:infoTable infoList="${tourSpotDescriptionVm.tourSpotDescriptionInfoVmList}" nameLabel="머리말"
                         descriptionLabel="본문" deleteUrl="/tour-spot/description/info/delete"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}" />
        </div>

        <jsp:include page="/WEB-INF/views/common/delete-confirm-modal.jsp"/>

        <t:infoFormModal ownerId="${tourSpotDescriptionVm.id}" ownerFieldName="tourSpotDescriptionId" ownerTypeLabel="여행지설명" nameLabel="머리말"
                         descriptionLabel="본문" urlPrefix="/tour-spot/description" includeDescription="true"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}"/>

        <t:infoDetailModal ownerName="${tourSpotDescriptionVm.name}" nameLabel="머리말" descriptionLabel="본문"
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
