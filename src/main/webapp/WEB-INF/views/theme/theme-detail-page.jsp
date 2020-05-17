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

<c:set var="pageTitle" value="${themeVm.name} 상세보기" />

<html>
<head>
    <title>${pageTitle}</title>
</head>

<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav theme="true" />
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <t:pathBarDirectory url="/theme/list" name="상위분류 리스트" />
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${themeVm.name} 상세보기" headingLabel="분류는 주제를 나누기 위한 일종의 목록이며, 사용자들이 정보를 쉽게 찾을 수 있도록 도와줍니다.
                예를 들어 상위분류:문화에는 하위분류:뮤지컬, 연극, 영화등이 있습니다." />

        </div>


        <div class="section__tab-list">
            <ul class="tab-list">
                <button type="button" class="tab-list__tab" data-target-id="tab-basic-info">기본정보</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-tag-list">하위분류 리스트</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-info-list">번역 리스트</button>
            </ul>
        </div>


        <div id="tab-basic-info" class="tab-content">
            <div class="layout__half">
                <div class="section__content border-box">
                    <t:contentWhiteBox label="아이디" value="${themeVm.id.toString()}"/>
                    <t:contentWhiteBox label="상위분류명" value="${themeVm.name}"/>
                    <t:contentWhiteBox label="활성화" value="${themeVm.enabled ? 'On' : 'Off'}"/>
                    <t:contentWhiteBox label="생성일" value="${themeVm.updatedAt}"/>
                    <t:contentWhiteBox label="편집일" value="${themeVm.updatedAt}"/>
                </div>

                <t:contentFooter id="${themeVm.id}" name="${themeVm.name}" urlPrefix="/theme" type="상위분류" />
            </div>
        </div>

        <div id="tab-tag-list" class="tab-content">
            <t:datatable includeDescription="false" includeOrder="false" nameLabel="하위분류명"
                         dataList="${themeVm.tagVmList}" datatableId="tag-data-table" urlPrefix="/tag"/>
        </div>

        <div id="tab-info-list" class="tab-content">
            <t:infoTable infoList="${themeVm.themeInfoVmList}" nameLabel="상위분류명"
                         descriptionLabel="" deleteUrl="/theme/info/delete"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}" />
        </div>

        <jsp:include page="/WEB-INF/views/common/delete-confirm-modal.jsp"/>

        <t:infoFormModal ownerId="${themeVm.id}" ownerFieldName="themeId" ownerTypeLabel="상위분류" nameLabel="상위분류명"
                         descriptionLabel="" urlPrefix="/theme" includeDescription="false"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}"/>

        <t:infoDetailModal ownerName="${themeVm.name}" nameLabel="상위분류" descriptionLabel="상위분류명"
                           includeTag="false" includeDescription="false"/>

    </jsp:body>

</t:masterPage>


<script>
    $(document).ready(function () {
        setDataTable('tag-data-table', false, 5, '${pageContext.request.contextPath}',
                     '/tag', '/add?themeId=${themeVm.id}', false, false);
        setListenerToTabList();
        setDeleteConfirmModal();
        $('button#delete-btn').on(eventType.click, onClickDeleteBtn);
        setInfoTable(false);
        setInfoFormModal();
        setInfoDetailModal();
    });
</script>

</body>
</html>
