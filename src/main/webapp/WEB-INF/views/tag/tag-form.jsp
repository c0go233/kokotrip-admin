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

<c:set var="pageTitle" value="${tagVm.id == null ? '하위분류' : tagVm.name} ${tagVm.id == null ? ' 추가하기' : ' 편집하기'}" />


<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>


<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav tag="true" />
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <c:choose>
                    <c:when test="${tagVm.themeId ==  null}">
                        <t:pathBarDirectory url="/tag/list" name="하위분류 리스트" />
                    </c:when>
                    <c:otherwise>
                        <t:pathBarDirectory url="/theme/list" name="상위분류 리스트" />
                        <t:pathBarDirectory url="/theme/detail/${tagVm.themeId}" name="${tagVm.themeName} 상세보기" />
                        <c:if test="${tagVm.id != null}">
                            <t:pathBarDirectory url="/tag/detail/${tagVm.id}" name="${tagVm.name} 상세보기" />
                        </c:if>
                    </c:otherwise>
                </c:choose>
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${pageTitle}" headingLabel="분류는 주제를 나누기 위한 일종의 목록이며, 사용자들이 정보를 쉽게 찾을 수 있도록 도와줍니다.
                예를 들어 상위분류:문화에는 하위분류:뮤지컬, 연극, 영화등이 있습니다." />

            <form:form action="${pageContext.request.contextPath}/tag/save" modelAttribute="tagVm" >

                <form:hidden path="id"/>

                <div class="section__content">
                    <t:sectionSubHeading subHeading="하위분류 기본정보" subHeadingLabel="하위분류 기본정보를 입력해주세요" />
                    <t:sectionException exceptionMessage="${exception}" />

                    <div class="border-box">
                        
                        <t:contentWhiteBoxDropdown label="상위분류명" key="${tagVm.themeId}" fieldName="themeId" items="${themeLinkedHashMap}">
                            <jsp:attribute name="errorSpan">
                                <form:errors path="themeId" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxDropdown>

                        <t:contentWhiteBoxInput label="하위분류명">
                            <jsp:attribute name="input">
                                <form:input path="name" cssClass="k-input"/>
                                <form:errors path="name" cssClass="field-error" />
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxCheckbox label="활성화" value="${tagVm.enabled}" fieldName="enabled" />
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
