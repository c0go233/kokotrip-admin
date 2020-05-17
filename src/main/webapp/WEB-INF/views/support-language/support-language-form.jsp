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

<c:set var="pageTitle" value="${supportLanguageVm.id == null ? '번역언어' : supportLanguageVm.name} ${supportLanguageVm.id == null ? ' 추가하기' : ' 편집하기'}" />


<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>


<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav supportLanguage="true" />
    </jsp:attribute>

    <jsp:body>


        <div class="layout__half">
            <div class="path-bar">
                <t:pathBarDirectory url="/support-language/list" name="번역언어 리스트" />
                <span>${pageTitle}</span>
            </div>

            <t:sectionHeading heading="${pageTitle}" headingLabel="번역(飜譯, translate)은 어떤 언어로 쓰인 글을 다른 언어로 된 상응하는 의미의 글로 전달하는 일이다. 이 때 전자의 언어를 원어 또는 출발어(source language)라 하고,
                후자의 언어를 번역어 또는 도착어(target language)라고 한다." />

            <form:form action="${pageContext.request.contextPath}/support-language/save" modelAttribute="supportLanguageVm" >

                <form:hidden path="id"/>

                <div class="section__content">
                    <t:sectionSubHeading subHeading="번역언어 기본정보" subHeadingLabel="번역언어 기본정보를 입력해주세요" />
                    <t:sectionException exceptionMessage="${exception}" />

                    <div class="border-box">

                        <t:contentWhiteBoxInput label="언어명">
                            <jsp:attribute name="input">
                                <form:input path="name" cssClass="k-input" placeHolder="e.g. 한국어, 영어, 일본어"/>
                                <form:errors path="name" cssClass="field-error" />
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxInput label="언어표시명">
                            <jsp:attribute name="input">
                                <form:input path="displayName" cssClass="k-input" placeHolder="e.g. 한국어, English, 日本語"/>
                                <form:errors path="displayName" cssClass="field-error" />
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxInput label="순서">
                            <jsp:attribute name="input">
                                <form:input path="order" cssClass="k-input"/>
                                <form:errors path="order" cssClass="field-error"/>
                            </jsp:attribute>
                        </t:contentWhiteBoxInput>

                        <t:contentWhiteBoxCheckbox label="활성화" value="${supportLanguageVm.enabled}" fieldName="enabled" />
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
