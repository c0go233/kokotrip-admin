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

<c:set var="pageTitle" value="${tourSpotTicketVm.name} 상세보기" />

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
                <t:pathBarDirectory url="/tour-spot/detail/${tourSpotTicketVm.tourSpotId}"
                                    name="${tourSpotTicketVm.tourSpotName} 상세보기" />
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${tourSpotTicketVm.name} 상세보기" headingLabel="${tourSpotTicketVm.description}"/>

        </div>


        <div class="section__tab-list">
            <ul class="tab-list">
                <button type="button" class="tab_selected tab-list__tab" data-target-id="tab-basic-info">기본정보</button>
                <button type="button" class="tab_selected tab-list__tab" data-target-id="tab-image">이미지</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-tour-spot-ticket-description-list">티켓설명리스트</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-info-list">번역리스트</button>
            </ul>
        </div>


        <div id="tab-basic-info" class="tab-content">
            <div class="layout__half">
                <div class="section__content border-box">
                    <t:contentWhiteBox label="아이디" value="${tourSpotTicketVm.id.toString()}" />
                    <t:contentWhiteBox label="티켓명" value="${tourSpotTicketVm.name}" />
                    <t:contentWhiteBox label="요약설명" value="${tourSpotTicketVm.description}"  />
                    <t:contentWhiteBox label="활성화" value="${tourSpotTicketVm.enabled}" />
                    <t:contentWhiteBox label="순서" value="${tourSpotTicketVm.order}" />
                    <t:contentWhiteBox label="생성일" value="${tourSpotTicketVm.updatedAt}" />
                    <t:contentWhiteBox label="편집일" value="${tourSpotTicketVm.updatedAt}" />
                </div>

                <div class="section__content border-box">
                    <div class="timetable-builder-wrapper timetable-display-wrapper">
                        <span class="timetable-display-heading">티켓타입</span>
                        <span class="timetable-display-heading">가격</span>
                        <span class="timetable-display-heading">대표가격</span>
                    </div>
                    <c:forEach var="ticketPrice" items="${tourSpotTicketVm.ticketPriceVmList}">
                        <div class="timetable-builder-wrapper timetable-display-wrapper">
                            <span>${ticketPrice.ticketTypeName}</span>
                            <span>${ticketPrice.price}</span>
                            <span>${ticketPrice.repPrice}</span>
                        </div>
                    </c:forEach>
                </div>

                <t:contentFooter id="${tourSpotTicketVm.id}" name="${tourSpotTicketVm.name}"
                                 urlPrefix="/tour-spot/ticket" type="여행지티켓" />

            </div>
        </div>

        <div id="tab-image" class="tab-content">
            <div class="layout__half">
                <t:imageGallery prefixUrl="/tour-spot/ticket/image"
                                imageList="${tourSpotTicketVm.baseImageVmList}"
                                ownerIdName="tourSpotTicketId"
                                includeRep="true"
                                ownerId="${tourSpotTicketVm.id}"/>
            </div>
        </div>


        <div id="tab-info-list" class="tab-content">

            <t:infoTable infoList="${tourSpotTicketVm.tourSpotTicketInfoVmList}" nameLabel="티켓명"
                         descriptionLabel="요약설명" deleteUrl="/tour-spot/ticket/info/delete"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}" />
        </div>

        <div id="tab-tour-spot-ticket-description-list" class="tab-content">
            <t:datatable includeDescription="true" includeOrder="true" nameLabel="머리말" descriptionLabel="본문"
                         dataList="${tourSpotTicketVm.tourSpotTicketDescriptionVmList}"
                         datatableId="tour-spot-ticket-description-data-table"
                         urlPrefix="/tour-spot/ticket/description"/>
        </div>

        <jsp:include page="/WEB-INF/views/common/delete-confirm-modal.jsp"/>

        <t:infoFormModal ownerId="${tourSpotTicketVm.id}" ownerFieldName="tourSpotTicketId" ownerTypeLabel="여행지티켓" nameLabel="티켓명"
                         descriptionLabel="요약설명" urlPrefix="/tour-spot/ticket" includeDescription="true"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}"/>

        <t:infoDetailModal ownerName="${tourSpotTicketVm.name}" nameLabel="머리말" descriptionLabel="본문"
                           includeTag="false" includeDescription="true"/>


    </jsp:body>

</t:masterPage>

<script>
    $(document).ready(function () {
        setDataTable('tour-spot-ticket-description-data-table', false, 5, '${pageContext.request.contextPath}',
            '/tour-spot/ticket/description', '/add?tourSpotTicketId=${tourSpotTicketVm.id}', true, true);
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
