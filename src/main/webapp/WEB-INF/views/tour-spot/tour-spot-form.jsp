<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-02-16
  Time: 00:30
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle"
       value="${tourSpotVm.id == null ? '여행지' : tourSpotVm.name} ${tourSpotVm.id == null ? ' 추가하기' : ' 편집하기'}"/>
<c:set var="subHeadingLabel"
       value="${tourSpotVm.id != null ? tourSpotVm.description :  '우리나라에 엄청난 관광객들이 몰려오고 있는 글로벌한 시대이다. 과거에는 부산이나 서울을 제외하고는 외국인 관광객들이 거의 관심을 두지 않았는데 시대가 바뀌고 지방의 도시들도 좋은 곳들이 많아짐에 따라 전국에 외국인 관광객의 발길이 닿지 않는 곳이 없을 정도다.'}"/>


<html>
<body>


<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav tourSpot="true"/>
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <t:pathBarDirectory url="/tour-spot/list" name="여행지 리스트"/>
                <c:if test="${tourSpotVm.id != null}">
                    <t:pathBarDirectory url="/tour-spot/detail/${tourSpotVm.id}" name="${tourSpotVm.name} 상세보기"/>
                </c:if>
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${pageTitle}" headingLabel="${subHeadingLabel}"/>

            <form:form id="tour-spot-form" action="${pageContext.request.contextPath}/tour-spot/save" modelAttribute="tourSpotVm">

                <form:hidden path="id"/>

                <div class="section__content">
                    <t:sectionSubHeading subHeading="여행지 기본정보" subHeadingLabel="여행지 기본정보를 입력해주세요"/>
                    <t:sectionException exceptionMessage="${exception}"/>

                    <div class="border-box">

                        <t:contentWhiteBoxDropdown label="도시" fieldName="cityId" items="${cityLinkedHashMap}"
                                                   key="${tourSpotVm.cityId}" id="city-dropdown">
                            <jsp:attribute name="errorSpan">
                                <form:errors path="cityId" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxDropdown>

                        <div class="content__white-box">
                            <div class="white-box__label-wrapper">
                                <label>유명지역</label>
                            </div>

                            <div class="white-box__right-side white-box__dropdown-wrapper">
                                <div id="region-dropdown" class="k-dropdown">
                                    <div class="k-input dropdown__select-wrapper">
                                        <input name="regionId" value="${tourSpotVm.regionId}" type="hidden">
                                        <span>선택안함</span>
                                        <i class="arrow down"></i>
                                    </div>
                                    <div class="dropdown__option-wrapper dropdown__option-box border-box">
                                        <button id="default-option" type="button" class="dropdown__item dropdown__option">
                                            <div>
                                                <span class="dropdown__item__text">선택안함</span>
                                            </div>
                                        </button>
                                        <c:forEach var="region" items="${regionVmList}">
                                            <button type="button" data-id="${region.id}" data-city-id="${region.cityId}"
                                                    class="dropdown__item dropdown__option hide" role="option"
                                                    aria-selected="${region.id == tourSpotVm.regionId}">
                                                <div>
                                                    <span class="dropdown__item__text">${region.name}</span>
                                                </div>
                                            </button>
                                        </c:forEach>
                                        <div class="empty-option">
                                            <span>표시할 유명지역이 없습니다</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <t:contentWhiteBoxInput label="여행지명">
                            <jsp:attribute name="input">
                                <form:input path="name" cssClass="k-input"/>
                                <form:errors path="name" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxInput label="요약설명">
                            <jsp:attribute name="input">
                                <form:textarea path="description" cssClass="k-input k-textarea"/>
                                <form:errors path="description" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxCheckbox label="활성화" value="${tourSpotVm.enabled}" fieldName="enabled"/>

                        <t:contentWhiteBoxDropdown label="하위분류" fieldName="tagId" items="${tagLinkedHashMap}"
                                                   key="${tourSpotVm.tagId}" id="tag-dropdown">
                            <jsp:attribute name="errorSpan">
                                <form:errors path="tagId" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxDropdown>

                        <t:contentWhiteBoxInput label="위도">
                            <jsp:attribute name="input">
                                <form:input path="latitude" cssClass="k-input"/>
                                <form:errors path="latitude" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxInput label="경도">
                            <jsp:attribute name="input">
                                <form:input path="longitude" cssClass="k-input"/>
                                <form:errors path="longitude" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxInput label="유명도">
                            <jsp:attribute name="input">
                                <form:input path="popularScore" cssClass="k-input"/>
                                <form:errors path="popularScore" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxInput label="웹사이트 링크">
                            <jsp:attribute name="input">
                                <form:input path="websiteLink" cssClass="k-input"/>
                                <form:errors path="websiteLink" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxInput label="연락처">
                            <jsp:attribute name="input">
                                <form:input path="contact" cssClass="k-input"/>
                                <form:errors path="contact" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxInput label="주소">
                            <jsp:attribute name="input">
                                <form:input path="address" cssClass="k-input"/>
                                <form:errors path="address" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxCheckbox label="항상열림" value="${tourSpotVm.alwaysOpen}" fieldName="alwaysOpen"/>


                    </div>
                </div>

                <div id="tour-spot-time-table-maker" class="section__content ${tourSpotVm.alwaysOpen ? 'hide' : ''}">
                <t:sectionSubHeading subHeading="여행지 시간표" subHeadingLabel="여행지 시간표를 입력해주세요"/>
                <form:errors element="TradingHourConstraint" cssClass="error" cssStyle="display: inline-block; margin-bottom:7px" />

                <div class="border-box">

                    <t:timetableBuilder dayOfWeekLinkedHashMap="${dayOfWeekLinkedHashMap}"
                                        tradingHourTypeLinkedHashMap="${tradingHourTypeLinkedHashMap}"
                                        tradingHourList="${tourSpotVm.tradingHourVmList}"/>

                </div>
            </div>


                <div class="content__footer">
                    <button type="submit" class="btn-bold btn-large k-btn">저장하기</button>
                </div>
            </form:form>
        </div>


    </jsp:body>

</t:masterPage>


<script>





    $(document).ready(function () {
        setRegionSelect();
        resetRegionSelect(false, '${tourSpotVm.cityId}');
        setTimetableBuilder();

        $('input[name=alwaysOpen]').on(eventType.click, function () { eTourSpotTimeTableMaker.toggleClass(htmlAttr.hide); });
        $('form#tour-spot-form').on('submit', setTradingHourInputs);

    });



</script>

</body>
</html>
