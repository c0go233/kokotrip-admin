<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-03-28
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle"
       value="${activityDescriptionVm.id == null ? '액티비티설명' : activityDescriptionVm.name} ${activityDescriptionVm.id == null ? ' 추가하기' : ' 편집하기'}"/>
<c:set var="subHeadingLabel"
       value="${activityDescriptionVm.id != null ? activityDescriptionVm.description :  '우리나라에 엄청난 관광객들이 몰려오고 있는 글로벌한 시대이다. 과거에는 부산이나 서울을 제외하고는 외국인 관광객들이 거의 관심을 두지 않았는데 시대가 바뀌고 지방의 여행지설명들도 좋은 곳들이 많아짐에 따라 전국에 외국인 관광객의 발길이 닿지 않는 곳이 없을 정도다.'}"/>

<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>

<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav activity="true"/>
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <t:pathBarDirectory url="/tour-spot/list" name="여행지 리스트"/>
                <t:pathBarDirectory url="/tour-spot/detail/${activityDescriptionVm.tourSpotId}"
                                    name="${activityDescriptionVm.tourSpotName} 상세보기"/>
                <t:pathBarDirectory url="/activity/detail/${activityDescriptionVm.activityId}"
                                    name="${activityDescriptionVm.activityName} 상세보기"/>
                <c:if test="${activityDescriptionVm.id != null}">
                    <t:pathBarDirectory url="/actvity/description/detail/${activityDescriptionVm.id}"
                                        name="${activityDescriptionVm.name} 상세보기"/>
                </c:if>
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${pageTitle}" headingLabel="${subHeadingLabel}"/>

            <form:form action="${pageContext.request.contextPath}/activity/description/save" modelAttribute="activityDescriptionVm">

                <form:hidden path="id"/>
                <form:hidden path="activityId"/>

                <div class="section__content">
                    <t:sectionSubHeading subHeading="액티비티설명 기본정보" subHeadingLabel="액티비티설명 기본정보를 입력해주세요"/>
                    <t:sectionException exceptionMessage="${exception}"/>

                    <div class="border-box">


                        <t:contentWhiteBoxInput label="머리말">
                            <jsp:attribute name="input">
                                <form:input path="name" cssClass="k-input"/>
                                <form:errors path="name" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxInput label="본문">
                            <jsp:attribute name="input">
                                <form:textarea path="description" cssClass="k-input k-textarea"/>
                                <form:errors path="description" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxCheckbox label="활성화" value="${activityDescriptionVm.enabled}"
                                                   fieldName="enabled"/>

                        <t:contentWhiteBoxInput label="순서">
                            <jsp:attribute name="input">
                                <form:input path="order" cssClass="k-input"/>
                                <form:errors path="order" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxCheckbox label="팝업" value="${activityDescriptionVm.popup}"
                                                   fieldName="popup"/>

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


</body>
</html>