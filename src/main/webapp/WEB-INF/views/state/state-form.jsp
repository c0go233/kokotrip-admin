<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-01-18
  Time: 21:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="pageTitle" value="${stateVm.id == null ? '시,도' : stateVm.name} ${stateVm.id == null ? ' 추가하기' : ' 편집하기'}" />

<html>
<head>
    <title>${pageTitle}</title>


</head>
<body>


<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav state="true" />
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <t:pathBarDirectory url="/state/list" name="시,도 리스트" />
                <c:if test="${stateVm.id != null}">
                    <t:pathBarDirectory url="/state/detail/${stateVm.id}" name="${stateVm.name} 상세보기" />
                </c:if>
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${pageTitle}" headingLabel="대한민국의 행정 구역(大韓民國의 行政 區域)은 대한민국의 통치권을 행사하는 지역에서 1개의 특별시,
                    6개의 광역시, 8개의 도, 1개의 특별자치도, 1개의 특별자치시로 구성된다. 이상 총 17개의 행정구역은 광역지방자치단체로 분류된다." />

            <form:form action="${pageContext.request.contextPath}/state/save" modelAttribute="stateVm" >

                <form:hidden path="id"/>

                <div class="section__content">
                    <t:sectionSubHeading subHeading="시,도 기본정보" subHeadingLabel="시,도 기본정보를 입력해주세요" />
                    <t:sectionException exceptionMessage="${exception}" />

                    <div class="border-box">

                        <t:contentWhiteBoxInput label="시,도명">
                            <jsp:attribute name="input">
                                <form:input path="name" cssClass="k-input"/>
                                <form:errors path="name" cssClass="field-error" />
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxCheckbox label="활성화" value="${stateVm.enabled}" fieldName="enabled" />
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
