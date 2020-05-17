<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-02-13
  Time: 09:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>


<html>
<head>
    <title>Title</title>
</head>
<body>


<t:masterPage>


    <jsp:attribute name="sideNav">
        <t:sideNav region="true" />
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <t:pathBarDirectory url="/state/list" name="시,도 리스트" />
                <t:pathBarDirectory url="/state/detail/${regionVm.stateId}" name="${regionVm.stateName} 상세보기" />
                <t:pathBarDirectory url="/city/detail/${regionVm.cityId}" name="${regionVm.cityName} 상세보기" />
                <span>${regionVm.name} 상세보기</span>
            </div>

            <t:sectionHeading heading="${regionVm.name} 상세보기" headingLabel="${regionVm.description}"/>

        </div>


        <div class="section__tab-list">
            <ul class="tab-list">
                <button type="button" class="tab_selected tab-list__tab" data-target-id="tab-basic-info">기본정보</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-info-list">번역리스트</button>
                <button type="button" class="tab-list__tab" data-target-id="tab-tag-list">분류리스트</button>
            </ul>
        </div>


        <div id="tab-basic-info" class="tab-content">
            <div class="layout__half">
                <div class="section__content border-box">
                    <t:contentWhiteBox label="아이디" value="${regionVm.id.toString()}" />
                    <t:contentWhiteBox label="유명지역명" value="${regionVm.name}" />
                    <t:contentWhiteBox label="요약설명" value="${regionVm.description}"  />
                    <t:contentWhiteBox label="활성화" value="${regionVm.enabled}" />
                    <t:contentWhiteBox label="위도" value="${regionVm.latitude.toString()}" />
                    <t:contentWhiteBox label="경도" value="${regionVm.longitude.toString()}" />
                    <t:contentWhiteBox label="생성일" value="${regionVm.updatedAt}" />
                    <t:contentWhiteBox label="편집일" value="${regionVm.updatedAt}" />
                </div>

                <t:contentFooter id="${regionVm.id}" name="${regionVm.name}" urlPrefix="/region" type="유명지역" />

            </div>
        </div>



        <div id="tab-tag-list" class="tab-content">
            <div class="layout__half">
                <div class="section__content border-box">
                    <c:forEach var="themeRel" items="${regionVm.themeRelVmList}">
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
            <t:infoTable infoList="${regionVm.regionInfoVmList}" nameLabel="유명지역명"
                         descriptionLabel="요약설명" deleteUrl="/region/info/delete"
                         supportLanguageLinkedHashMap="${supportLanguageHashMap}" />
        </div>

        <jsp:include page="/WEB-INF/views/common/delete-confirm-modal.jsp"/>

        <t:infoFormModal ownerId="${regionVm.id}" ownerFieldName="regionId" ownerTypeLabel="유명지역" nameLabel="유명지역명"
                         descriptionLabel="요약설명" urlPrefix="/region" includeDescription="true" supportLanguageLinkedHashMap="${supportLanguageHashMap}"/>

        <t:infoDetailModal ownerName="${regionVm.name}" nameLabel="도시명" descriptionLabel="요약설명"
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
