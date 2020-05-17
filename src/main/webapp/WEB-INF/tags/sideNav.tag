<%@ tag description="Main Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="dashboard" type="java.lang.Boolean" %>
<%@ attribute name="state" type="java.lang.Boolean" %>
<%@ attribute name="city" type="java.lang.Boolean" %>
<%@ attribute name="region" type="java.lang.Boolean" %>
<%@ attribute name="tourSpot" type="java.lang.Boolean" %>
<%@ attribute name="activity" type="java.lang.Boolean" %>
<%@ attribute name="theme" type="java.lang.Boolean" %>
<%@ attribute name="tag" type="java.lang.Boolean" %>
<%@ attribute name="ticketType" type="java.lang.Boolean" %>
<%@ attribute name="supportLanguage" type="java.lang.Boolean" %>


<c:set var="linkSelected" value="side-nav__link-selected"/>
<c:set var="dropdownOpen" value="dropdown-open"/>
<c:set var="urlPrefix" value="${pageContext.request.contextPath}"/>

<div class="side-nav-container">
    <div class="side-nav__toggle-container">
        <i class="fas fa-bars m-icon"></i>
    </div>
    <nav id="side-nav" role="navigation" class="side-nav">
        <ul>
            <li>
                <a role="button" href="${urlPrefix}/" tabindex="0"
                   class="side-nav__link ${dashboard ? linkSelected : ''}">
                    <div class="side-nav__menu-item">
                        <div class="icon-container">
                            <img src="${pageContext.request.contextPath}/resources/image/dashboard.png" />
                        </div>
                        <div class="side-nav__menu-time-title">
                            <span>대쉬보드</span>
                        </div>
                    </div>
                </a>
            </li>

            <li>
                <div class="side-nav__dropdown ${state || city || region ? dropdownOpen : ''}">
                    <button type="button" class="side-nav__link side-nav__dropdown-btn">
                        <div class="side-nav__menu-item">
                            <div class="icon-container">
                                <img src="${pageContext.request.contextPath}/resources/image/world.png" />
                            </div>
                            <div class="side-nav__menu-time-title">
                                <span>지역</span>
                            </div>
                            <div class="icon-container">
                                <i class="arrow down"></i>
                            </div>
                        </div>

                    </button>
                    <div class="side-nav__dropdown-menu">
                        <a role="button" href="${urlPrefix}/state/list"
                           class="side-nav__link ${state ? linkSelected : ''}">
                            <div class="side-nav__menu-item">
                                <div class="side-nav__menu-time-title">
                                    <span>시,도</span>
                                </div>
                            </div>
                        </a>
                        <a role="button" href="${urlPrefix}/city/list"
                           class="side-nav__link ${city ? linkSelected : ''}">
                            <div class="side-nav__menu-item">
                                <div class="side-nav__menu-time-title">
                                    <span>도시</span>
                                </div>
                            </div>
                        </a>
                        <a role="button" href="${urlPrefix}/region/list"
                           class="side-nav__link ${region ? linkSelected : ''}">
                            <div class="side-nav__menu-item">
                                <div class="side-nav__menu-time-title">
                                    <span>유명동네</span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
            </li>

            <li>
                <div class="side-nav__dropdown ${tourSpot || activity ? dropdownOpen : ''}">
                    <button type="button" class="side-nav__link side-nav__dropdown-btn">
                        <div class="side-nav__menu-item">
                            <div class="icon-container">
                                <img src="${urlPrefix}/resources/image/marker.png" />
                            </div>
                            <div class="side-nav__menu-time-title">
                                <span>여행지</span>
                            </div>
                            <div class="icon-container">
                                <i class="arrow down"></i>
                            </div>
                        </div>

                    </button>
                    <div class="side-nav__dropdown-menu">
                        <a role="button" href="${urlPrefix}/tour-spot/list"
                           class="side-nav__link ${tourSpot ? linkSelected : ''}">
                            <div class="side-nav__menu-item">
                                <div class="side-nav__menu-time-title">
                                    <span>여행지</span>
                                </div>
                            </div>
                        </a>
                        <a role="button" href="${urlPrefix}/activity/list"
                           class="side-nav__link ${activity ? linkSelected : ''}">
                            <div class="side-nav__menu-item">
                                <div class="side-nav__menu-time-title">
                                    <span>액티비티</span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>

            </li>

            <li>
                <div class="side-nav__dropdown ${theme || tag ? dropdownOpen : ''}">
                    <button type="button" class="side-nav__link side-nav__dropdown-btn">
                        <div class="side-nav__menu-item">
                            <div class="icon-container">
                                <img src="${urlPrefix}/resources/image/price.png" />
                            </div>
                            <div class="side-nav__menu-time-title">
                                <span>카테고리</span>
                            </div>
                            <div class="icon-container">
                                <i class="arrow down"></i>
                            </div>
                        </div>

                    </button>
                    <div class="side-nav__dropdown-menu">
                        <a role="button" href="${urlPrefix}/theme/list"
                           class="side-nav__link ${theme ? linkSelected : ''}">
                            <div class="side-nav__menu-item">
                                <div class="side-nav__menu-time-title">
                                    <span>상위분류</span>
                                </div>
                            </div>
                        </a>
                        <a role="button" href="${urlPrefix}/tag/list"
                           class="side-nav__link ${tag ? linkSelected : ''}">
                            <div class="side-nav__menu-item">
                                <div class="side-nav__menu-time-title">
                                    <span>하위분류</span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>

            </li>

            <li>
                <a role="button" href="${urlPrefix}/ticket-type/list" tabindex="0"
                   class="side-nav__link ${ticketType ? linkSelected : ''}">
                    <div class="side-nav__menu-item">
                        <div class="icon-container">
                            <img src="${pageContext.request.contextPath}/resources/image/ticket.png" />
                        </div>
                        <div class="side-nav__menu-time-title">
                            <span>티켓타입</span>
                        </div>
                    </div>
                </a>
            </li>

            <li>
                <a role="button" href="${pageContext.request.contextPath}/support-language/list" tabindex="0"
                   class="side-nav__link ${supportLanguage ? linkSelected : ''}">
                    <div class="side-nav__menu-item">
                        <div class="icon-container">
                            <img src="${pageContext.request.contextPath}/resources/image/language.png" />
                        </div>
                        <div class="side-nav__menu-time-title">
                            <span>번역언어</span>
                        </div>
                    </div>
                </a>
            </li>

        </ul>
    </nav>
</div>