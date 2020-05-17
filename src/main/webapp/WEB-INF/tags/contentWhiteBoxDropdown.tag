<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ attribute required="true" name="label" type="java.lang.String" %>
<%@ attribute required="true" name="key" type="java.lang.Integer" %>
<%@ attribute required="true" name="fieldName" type="java.lang.String" %>
<%@ attribute required="true" name="items" type="java.util.LinkedHashMap" %>
<%@ attribute name="errorSpan" fragment="true" %>
<%@ attribute name="id" type="java.lang.String" %>



<div class="content__white-box">
    <div class="white-box__label-wrapper">
        <label>${label}</label>
    </div>

    <div class="white-box__right-side white-box__dropdown-wrapper">
        <div id="${id}" class="k-dropdown">
            <div class="k-input dropdown__select-wrapper">
                <input name="${fieldName}" value="${key}" type="hidden">
                <span>${key != null ? items.get(key) : '선택안함'}</span>
                <i class="arrow down"></i>
            </div>
            <div class="dropdown__option-wrapper dropdown__option-box">
                <c:forEach var="option" items="${items}">
                    <button type="button" data-id="${option.key}" class="dropdown__item dropdown__option" role="option"
                            aria-selected="${option.key == key}">
                        <div>
                            <span class="dropdown__item__text">${option.value}</span>
                        </div>
                    </button>
                </c:forEach>
            </div>
        </div>
        <jsp:invoke fragment="errorSpan" />

    </div>
</div>