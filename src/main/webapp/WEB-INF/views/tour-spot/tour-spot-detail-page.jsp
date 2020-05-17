<%--
  Created by IntelliJ IDEA.
  User: mtae
  Date: 21/02/2020
  Time: 3:35 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle" value="${tourSpotVm.name} 상세보기"/>

<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>


<t:masterPage>


    <jsp:attribute name="sideNav">
        <t:sideNav tourSpot="true"/>
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <t:pathBarDirectory url="/tour-spot/list" name="여행지 리스트"/>
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${tourSpotVm.name} 상세보기" headingLabel="${tourSpotVm.description}"/>

        </div>


        <div class="section__tab-list">
            <ul class="tab-list">
                <button type="button" class="tab_selected tab-list__tab" data-target-id="tab-basic-info">기본정보</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-info-list">번역리스트</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-tour-spot-description-list">여행지설명 리스트
                </button>
                <button type="button" class="tab-list__tab" data-target-id="tab-tour-spot-ticket-list">여행지티켓 리스트
                </button>
                <button type="button" class="tab-list__tab" data-target-id="tab-activity-list">액티비티 리스트
                </button>
                <button type="button" class="tab-list__tab" data-target-id="tab-photo-zone-list">포토존 리스트
                </button>
            </ul>
        </div>


        <div id="tab-basic-info" class="tab-content">
            <div class="layout__half">
                <div class="section__content border-box">
                    <t:contentWhiteBox label="아이디" value="${tourSpotVm.id.toString()}"/>
                    <t:contentWhiteBox label="도시명" value="${tourSpotVm.cityName}"/>
                    <t:contentWhiteBox label="유명지역명" value="${tourSpotVm.regionName}"/>
                    <t:contentWhiteBox label="여행지명" value="${tourSpotVm.name}"/>
                    <t:contentWhiteBox label="요약설명" value="${tourSpotVm.description}"
                                       classForWhiteBox="content__description-box"/>
                    <t:contentWhiteBox label="활성화" value="${tourSpotVm.enabled}"/>
                    <t:contentWhiteBox label="위도" value="${tourSpotVm.latitude.toString()}"/>
                    <t:contentWhiteBox label="경도" value="${tourSpotVm.longitude.toString()}"/>
                    <t:contentWhiteBox label="항상열림" value="${tourSpotVm.alwaysOpen}"/>

                    <t:contentWhiteBox label="주소" value="${tourSpotVm.address}"/>
                    <t:contentWhiteBox label="연락처" value="${tourSpotVm.contact}"/>
                    <t:contentWhiteBox label="웹사이트" value="${tourSpotVm.websiteLink}"/>
                    <t:contentWhiteBox label="별점" value="${tourSpotVm.averageRate}"/>
                    <t:contentWhiteBox label="별점개수" value="${tourSpotVm.numberOfRate}"/>
                    <t:contentWhiteBox label="유명도" value="${tourSpotVm.popularScore}"/>
                    <t:contentWhiteBox label="위시리스트개수" value="${tourSpotVm.numberOfWishListSaved}"/>
                    <t:contentWhiteBox label="하위분류" value="${tourSpotVm.tagName}"/>

                    <t:contentWhiteBox label="생성일" value="${tourSpotVm.updatedAt}"/>
                    <t:contentWhiteBox label="편집일" value="${tourSpotVm.updatedAt}"/>
                </div>

                <c:if test="${!tourSpotVm.alwaysOpen}">
                    <div class="section__content border-box">
                        <div class="timetable-builder-wrapper timetable-display-wrapper">
                            <span class="timetable-display-heading">요일</span>
                            <span class="timetable-display-heading">영업유무</span>
                            <span class="timetable-display-heading">오픈</span>
                            <span class="timetable-display-heading">마감</span>
                        </div>
                        <c:forEach var="tradingHour" items="${tourSpotVm.tradingHourVmList}">
                            <div class="timetable-builder-wrapper timetable-display-wrapper">
                                <span>${dayOfWeekLinkedHashMap.get(tradingHour.dayOfWeekId)}</span>
                                <span>${tradingHourTypeLinkedHashMap.get(tradingHour.tradingHourTypeId)}</span>
                                <span>${tradingHour.openTime.hours < 10 ? '0' : ''}${tradingHour.openTime.hours}
                                      :${tradingHour.openTime.minutes < 10 ? '0' : ''}${tradingHour.openTime.minutes}</span>
                                <span>${tradingHour.closeTime.hours < 10 ? '0' : ''}${tradingHour.closeTime.hours}
                                      :${tradingHour.closeTime.minutes < 10 ? '0' : ''}${tradingHour.closeTime.minutes}</span>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>


                <t:contentFooter id="${tourSpotVm.id}" name="${tourSpotVm.name}" urlPrefix="/tour-spot" type="여행지"/>

            </div>
        </div>

        <div id="tab-tour-spot-description-list" class="tab-content">
            <t:datatable includeDescription="true" includeOrder="true" nameLabel="머리말" descriptionLabel="본문"
                         dataList="${tourSpotVm.tourSpotDescriptionVmList}"
                         datatableId="tour-spot-description-data-table"
                         urlPrefix="/tour-spot/description"/>
        </div>

        <div id="tab-tour-spot-ticket-list" class="tab-content">
            <t:datatable includeDescription="true" includeOrder="true" nameLabel="여행지티켓명" descriptionLabel="요약설명"
                         dataList="${tourSpotVm.tourSpotTicketVmList}" datatableId="tour-spot-ticket-data-table"
                         urlPrefix="/tour-spot/ticket"/>
        </div>

        <div id="tab-activity-list" class="tab-content">
            <t:datatable includeDescription="true" includeOrder="true" nameLabel="액티비티명" descriptionLabel="요약설명"
                         dataList="${tourSpotVm.activityVmList}" datatableId="activity-data-table"
                         urlPrefix="/activity"/>
        </div>

        <div id="tab-photo-zone-list" class="tab-content">
            <t:datatable includeDescription="true" includeOrder="true" nameLabel="포토존명" descriptionLabel="요약설명"
                         dataList="${tourSpotVm.photoZoneVmList}" datatableId="photo-zone-data-table"
                         urlPrefix="/photo-zone"/>
        </div>

        <div id="tab-info-list" class="tab-content">

            <t:infoTable infoList="${tourSpotVm.tourSpotInfoVmList}" nameLabel="여행지명"
                         descriptionLabel="요약설명" deleteUrl="/tour-spot/info/delete"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}"/>
        </div>

        <jsp:include page="/WEB-INF/views/common/delete-confirm-modal.jsp"/>

        <t:infoFormModal ownerId="${tourSpotVm.id}" ownerFieldName="tourSpotId" ownerTypeLabel="여행지" nameLabel="여행지명"
                         descriptionLabel="요약설명" urlPrefix="/tour-spot" includeDescription="true"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}"/>

        <t:infoDetailModal ownerName="${tourSpotVm.name}" nameLabel="여행지명" descriptionLabel="요약설명"
                           includeTag="true" includeDescription="true"/>


    </jsp:body>

</t:masterPage>

<script>
    $(document).ready(function () {
        setDataTable('tour-spot-description-data-table', false, 5, '${pageContext.request.contextPath}',
                     '/tour-spot/description', '/add?tourSpotId=${tourSpotVm.id}', true, true);
        setDataTable('tour-spot-ticket-data-table', false, 5, '${pageContext.request.contextPath}',
                     '/tour-spot/ticket', '/add?tourSpotId=${tourSpotVm.id}', true, true);
        setDataTable('activity-data-table', false, 5, '${pageContext.request.contextPath}',
                     '/activity', '/add?tourSpotId=${tourSpotVm.id}', true, true);
        setDataTable('photo-zone-data-table', false, 5, '${pageContext.request.contextPath}',
            '/photo-zone', '/add?parentTourSpotId=${tourSpotVm.id}', true, true);

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
