<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-02-29
  Time: 09:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle" value="${ticketTypeVm.id == null ? '티켓타입' : ticketTypeVm.name} ${ticketTypeVm.id == null ? ' 추가하기' : ' 편집하기'}" />


<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>


<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav ticketType="true" />
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <t:pathBarDirectory url="/ticket-type/list" name="티켓타입 리스트" />
                <c:if test="${ticketTypeVm.id != null}">
                    <t:pathBarDirectory url="/ticket-type/detail/${ticketTypeVm.id}" name="${ticketTypeVm.name} 상세보기" />
                </c:if>
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${pageTitle}" headingLabel="티켓타입은 어른, 어린이, 장애우, 국가유공자, 경로자 등과 같이 티켓 구매자를 정의하는데 상용된다." />

            <form:form action="${pageContext.request.contextPath}/ticket-type/save" modelAttribute="ticketTypeVm" >

                <form:hidden path="id"/>

                <div class="section__content">
                    <t:sectionSubHeading subHeading="티켓타입 기본정보" subHeadingLabel="티켓타입 기본정보를 입력해주세요" />
                    <t:sectionException exceptionMessage="${exception}" />

                    <div class="border-box">

                        <t:contentWhiteBoxInput label="티켓타입명">
                            <jsp:attribute name="input">
                                <form:input path="name" cssClass="k-input"/>
                                <form:errors path="name" cssClass="field-error" />
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxCheckbox label="활성화" value="${ticketTypeVm.enabled}" fieldName="enabled" />
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
