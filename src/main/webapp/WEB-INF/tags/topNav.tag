<%@ tag description="Main Template" pageEncoding="UTF-8" %>

<div class="top-nav-container">
    <div class="top-nav__left-bar">
        <a class="top-nav__logo-link" href="${pageContext.request.contextPath}/dashboard">
            <div class="top-nav__logo-container">
                <div class="icon-container top-nav__icon-container">
<%--                    <i class="fab fa-kickstarter m-icon"></i>--%>
                    <img src="${pageContext.request.contextPath}/resources/image/letter-k.png" class="medium-icon">
                </div>
                <span class="top-nav__logo-title">KOKOTRIP ADMIN</span>
            </div>
        </a>
    </div>

    <div class="top-nav__right-bar">
        <div class="m-icon icon-container top-nav__icon-container">
            <i class="far fa-bell"></i>
        </div>
        <div class="m-icon icon-container top-nav__icon-container">
            <i class="fas fa-cog"></i>
        </div>
        <div class="m-icon icon-container top-nav__icon-container">
            <i class="fas fa-user-circle"></i>
        </div>

        <div class="top-nav__item">
            <div class="top-nav__btn-wrapper">
                <button id="user-profile-btn" class="k-btn top-nav__btn" data-target-id="user-profile-menu">
                    <img src="${pageContext.request.contextPath}/resources/image/person.png">
                </button>
            </div>

            <div id="user-profile-menu" class="top-nav__drop-down-menu-wrapper border-box hide">
                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <button type="submit" class="k-btn link-primary top-nav__drop-down-menu-btn">로그아웃</button>
                </form>
            </div>
        </div>

    </div>
</div>