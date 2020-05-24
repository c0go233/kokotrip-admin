<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-01-21
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="pageTitle" value="${cityVm.id == null ? '도시' : cityVm.name} ${cityVm.id == null ? ' 추가하기' : ' 편집하기'}"/>
<c:set var="subHeadingLabel" value="${cityVm.id == null ? '대한민국의 시(市)는 1949년 8월 15일 19개의 부(府)를 일괄 개칭하여 탄생되었다. 다음의 세 종류로 나눌 수 있다. 광역자치단체인 특별시는 서울특별시 뿐이다. 광역시는 인천광역시, 대전광역시, 광주광역시, 대구광역시, 부산광역시, 울산광역시이다. 특별자치시는 세종특별자치시 뿐이다. 기초자치단체인 시는 도 산하에 둔다. 제주특별자치도의 하부 행정 구역으로 지방자치단체가 아닌 행정시는 제주시와 서귀포시이다.' : cityVm.description}"/>


<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>

<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav city="true" />
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <c:choose>
                    <c:when test="${cityVm.stateId == null}">
                        <t:pathBarDirectory url="/city/list" name="도시리스트" />
                    </c:when>
                    <c:otherwise>
                        <t:pathBarDirectory url="/state/list" name="시,도 리스트" />
                        <t:pathBarDirectory url="/state/detail/${cityVm.stateId}" name="${cityVm.stateName} 상세보기" />
                        <c:if test="${cityVm.id != null}">
                            <t:pathBarDirectory url="/city/detail/${cityVm.id}" name="${cityVm.name} 상세보기" />
                        </c:if>
                    </c:otherwise>
                </c:choose>
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${pageTitle}" headingLabel="${subHeadingLabel}"/>

            <form:form action="${pageContext.request.contextPath}/city/save" modelAttribute="cityVm">

                <form:hidden path="id" id="cityId"/>

                <div class="section__content">
                    <t:sectionSubHeading subHeading="도시 기본정보" subHeadingLabel="도시 기본정보를 입력해주세요"/>
                    <t:sectionException exceptionMessage="${exception}"/>

                    <div class="border-box">

                        <t:contentWhiteBoxDropdown label="시,도명" fieldName="stateId" items="${stateLinkedHashMap}"
                                                   key="${cityVm.stateId}">
                            <jsp:attribute name="errorSpan">
                                <form:errors path="stateId" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxDropdown>

                        <t:contentWhiteBoxInput label="도시명">
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

                        <t:contentWhiteBoxCheckbox label="활성화" value="${cityVm.enabled}" fieldName="enabled"/>

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
