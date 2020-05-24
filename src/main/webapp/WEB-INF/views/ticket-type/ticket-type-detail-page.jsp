<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-03-01
  Time: 09:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle" value="${ticketTypeVm.name} 상세보기" />

<html>
<head>
    <title>${pageTitle}</title>
</head>

<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav ticketType="true" />
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <t:pathBarDirectory url="/ticket-type/list" name="티켓타입 리스트" />
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${ticketTypeVm.name} 상세보기" headingLabel="티켓타입은 어른, 어린이, 장애우, 국가유공자, 경로자 등과 같이 티켓 구매자를 정의하는데 상용된다." />

        </div>


        <div class="section__tab-list">
            <ul class="tab-list">
                <button type="button" class="tab-list__tab" data-target-id="tab-basic-info">기본정보</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-info-list">번역 리스트</button>
            </ul>
        </div>


        <div id="tab-basic-info" class="tab-content">
            <div class="layout__half">
                <div class="section__content border-box">
                    <t:contentWhiteBox label="아이디" value="${ticketTypeVm.id.toString()}"/>
                    <t:contentWhiteBox label="티켓타입명" value="${ticketTypeVm.name}"/>
                    <t:contentWhiteBox label="활성화" value="${ticketTypeVm.enabled ? 'On' : 'Off'}"/>
                    <t:contentWhiteBox label="생성일" value="${ticketTypeVm.updatedAt}"/>
                    <t:contentWhiteBox label="편집일" value="${ticketTypeVm.updatedAt}"/>
                </div>

                <t:contentFooter id="${ticketTypeVm.id}" name="${ticketTypeVm.name}" urlPrefix="/ticket-type" type="티켓타입" />
            </div>
        </div>

        <div id="tab-info-list" class="tab-content">
            <t:infoTable infoList="${ticketTypeVm.ticketTypeInfoVmList}" nameLabel="티켓타입명"
                         descriptionLabel="" deleteUrl="/ticket-type/info/delete"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}"/>
        </div>

        <jsp:include page="/WEB-INF/views/common/delete-confirm-modal.jsp"/>

        <t:infoFormModal ownerId="${ticketTypeVm.id}" ownerFieldName="ticketTypeId" ownerTypeLabel="티켓타입" nameLabel="티켓타입명"
                         descriptionLabel="" urlPrefix="/ticket-type" includeDescription="false"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}"/>

        <t:infoDetailModal ownerName="${ticketTypeVm.name}" nameLabel="티켓타입" descriptionLabel="티켓타입명"
                           includeTag="false" includeDescription="false"/>

    </jsp:body>

</t:masterPage>


<script>
    $(document).ready(function () {
        setListenerToTabList();
        setDeleteConfirmModal();
        $('button#delete-btn').on(eventType.click, onClickDetailDeleteBtn);
        setInfoTable(false);
        setInfoFormModal();
        setInfoDetailModal();
    });
</script>

</body>
</html>
