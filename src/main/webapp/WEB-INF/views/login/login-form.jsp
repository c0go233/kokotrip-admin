<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-01-15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<head>
    <title>로그인</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/kokotripadmin.css">
</head>
<body>
<%--<div class="login-page__background-image"--%>
<%--     style="background-image: url('${pageContext.request.contextPath}/resources/image/busan.jpg');"></div>--%>
<div class="screen-filter"></div>

<div class="layout__child_center">



    <div class="login-form-wrapper border-box">

        <div class="login-form-wrapper__left-side"
             style="background-image: url('${pageContext.request.contextPath}/resources/image/Gyeongbokgung.jpg');">
            <div class="screen-filter"></div>

            <div class="login-form-wrapper__caption-wrapper">

                <div>
                    <img class="logo-big" src="${pageContext.request.contextPath}/resources/image/letter-k-64.png">
                </div>
                <span class="logo-label-big">KOKOTRIPADMIN</span>
                <p>Experience the excitement!
                    Experience. Tourism. These are as education in themselves.
                    Feed your wanderlust.
                    Fill those passport pages.
                    Find your escape.</p>
            </div>
        </div>

        <div class="login-form-wrapper__right-side">

            <div>WELCOME TO</div>
            <div class="login-form__right-side__logo-container">
                <img src="${pageContext.request.contextPath}/resources/image/letter-k-64-primary.png">
                <span>KOKOTRIPADMIN</span>
            </div>
            <div>
                Experience the excitement!
                Experience. Tourism. These are as education in themselves.
                Feed your wanderlust.
            </div>

            <form:form action="${pageContext.request.contextPath}/authenticateTheUser"
                       method="post"
                       cssClass="login-form">

                <c:if test="${param.error != null}">
                    <div class="login-form__error-wrapper">
                        <span>로그인 할수없습니다. 이메일과 비밀번호를 확인해주세요</span>
                    </div>
                </c:if>

                <c:if test="${param.logout != null}">
                    <div class="login-form__logout-wrapper">
                        <span>정상적으로 로그아웃하였습니다.</span>
                    </div>
                </c:if>

                <div>
                    <label for="username">이메일</label>
                    <input id="username" type="text" name="username" class="k-input">
                </div>
                <div>
                    <label for="password">비밀번호</label>
                    <input id="password" type="password" name="password" class="k-input">
                </div>

                <button type="submit" class="btn-bold btn-stretch">로그인</button>
            </form:form>

        </div>


    </div>


</div>


<%--    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/kokotripadmin.js"></script>--%>

</body>
</html>
