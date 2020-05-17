<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-01-25
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>


<c:set var="pageTitle" value="${regionVm.id == null ? '유명지역' : regionVm.name} ${regionVm.id == null ? ' 추가하기' : ' 편집하기'}"/>
<c:set var="subHeadingLabel" value="${regionVm.id != null ? regionVm.description : '그냥 동이라고 하면 행정동을 말한다. 법정동과 구별하고 있으며, 원칙적으로는 모든 행정동에 동주민센터가 있다. 그러나 재건축이나 재개발에 의해 주민이 없거나 아예 없어지면 일시적으로 이웃한 동주민센터에서 업무를 보기도 한다.'}"/>


<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>

<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav region="true" />
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <c:choose>
                    <c:when test="${regionVm.cityId == null}">
                        <t:pathBarDirectory url="/region/list" name="유명지역리스트" />
                    </c:when>
                    <c:otherwise>
                        <t:pathBarDirectory url="/state/list" name="시,도 리스트" />
                        <t:pathBarDirectory url="/state/detail/${regionVm.stateId}" name="${regionVm.stateName} 상세보기" />
                        <t:pathBarDirectory url="/city/detail/${regionVm.cityId}" name="${regionVm.cityName} 상세보기" />
                        <c:if test="${regionVm.id != null}">
                            <t:pathBarDirectory url="/region/detail/${regionVm.id}" name="${regionVm.name} 상세보기" />
                        </c:if>
                    </c:otherwise>
                </c:choose>
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${pageTitle}" headingLabel="${subHeadingLabel}"/>

            <form:form action="${pageContext.request.contextPath}/region/save" modelAttribute="regionVm">

                <form:hidden path="id"/>

                <div class="section__content">
                    <t:sectionSubHeading subHeading="유명지역 기본정보" subHeadingLabel="유명지역 기본정보를 입력해주세요"/>
                    <t:sectionException exceptionMessage="${exception}"/>

                    <div class="border-box">

                        <t:contentWhiteBoxDropdown label="도시명" fieldName="cityId" items="${cityLinkedHashMap}" key="${regionVm.cityId}">
                            <jsp:attribute name="errorSpan">
                                <form:errors path="cityId" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxDropdown>

                        <t:contentWhiteBoxInput label="유명지역명">
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

                        <t:contentWhiteBoxCheckbox label="활성화" value="${regionVm.enabled}" fieldName="enabled"/>

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
