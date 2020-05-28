<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-03-09
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>


<c:set var="pageTitle"
       value="${photoZoneVm.id == null ? '포토존' : photoZoneVm.name} ${photoZoneVm.id == null ? ' 추가하기' : ' 편집하기'}"/>
<c:set var="subHeadingLabel"
       value="${photoZoneVm.id != null ? photoZoneVm.description : '우리나라에 엄청난 관광객들이 몰려오고 있는 글로벌한 시대이다. 과거에는 부산이나 서울을 제외하고는 외국인 관광객들이 거의 관심을 두지 않았는데 시대가 바뀌고 지방의 여행지설명들도 좋은 곳들이 많아짐에 따라 전국에 외국인 관광객의 발길이 닿지 않는 곳이 없을 정도다.'}"/>

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
                <t:pathBarDirectory url="/tour-spot/detail/${photoZoneVm.parentTourSpotId}"
                                    name="${photoZoneVm.parentTourSpotName} 상세보기"/>
                <c:if test="${photoZoneVm.id != null}">
                    <t:pathBarDirectory url="/phot-zone/detail/${photoZoneVm.id}"
                                        name="${photoZoneVm.name} 상세보기"/>
                </c:if>
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${pageTitle}" headingLabel="${subHeadingLabel}"/>

            <form:form action="${pageContext.request.contextPath}/photo-zone/save" modelAttribute="photoZoneVm">

                <form:hidden path="id"/>
                <form:hidden path="parentTourSpotId"/>

                <div class="section__content">
                    <t:sectionSubHeading subHeading="포토존 기본정보" subHeadingLabel="포토존 기본정보를 입력해주세요"/>
                    <t:sectionException exceptionMessage="${exception}"/>

                    <div class="border-box">

                        <t:contentWhiteBoxInput label="포토존명">
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


                        <t:contentWhiteBoxAutoComplete label="연계여행지"
                                                       url="${pageContext.request.contextPath}/tour-spot/auto-complete-list"
                                                       valueForSelect="${photoZoneVm.tourSpotName}"
                                                       placeHolder="여행지를 입력해주세요"
                                                       idForUnselectBtn="tour-spot-auto-complete-unselect-btn"
                                                       classForOptionWrapper="tour-spot-auto-complete-option-wrapper">
                            <jsp:attribute name="input">
                                <form:hidden path="tourSpotId" cssClass="auto-complete-input"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxAutoComplete>

                        <t:contentWhiteBoxAutoComplete label="연계액티비티"
                                                       url="${pageContext.request.contextPath}/activity/auto-complete-list"
                                                       valueForSelect="${photoZoneVm.activityName}"
                                                       placeHolder="액티비티를 입력해주세요"
                                                       idForUnselectBtn="activity-auto-complete-unselect-btn"
                                                       classForOptionWrapper="activity-auto-complete-option-wrapper">
                            <jsp:attribute name="input">
                                <form:hidden path="activityId" cssClass="auto-complete-input"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxAutoComplete>


                        <t:contentWhiteBoxCheckbox label="활성화" value="${photoZoneVm.enabled}" fieldName="enabled"/>

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


                        <t:contentWhiteBoxInput label="순서">
                            <jsp:attribute name="input">
                                <form:input path="order" cssClass="k-input"/>
                                <form:errors path="order" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                    </div>
                </div>

                <div class="content__footer">
                        <%--<a href="${pageContext.request.contextPath}/state/list" class="btn-light btn-large btn ">취소하기</a>--%>
                    <button type="submit" class="btn-bold btn-large k-btn">저장하기</button>
                </div>
            </form:form>
        </div>


    </jsp:body>

</t:masterPage>


<script>
    $(document).ready(function () {
        setPhotoZoneAutoCompleteSelect();
        setAutoCompleteSelect();
        // setTicketPriceBuilder();
        // $('form#tour-spot-ticket-form').on(eventType.click, setTicketPriceInputs);
    });

</script>

</body>
</html>
