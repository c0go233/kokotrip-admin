<%@ tag description="Main Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute required="true" name="defaultTime" type="java.lang.Integer" %>
<%@ attribute required="true" name="maximumTime" type="java.lang.Integer" %>
<%@ attribute required="true" name="dropdownInputId" type="java.lang.String" %>
<%@ attribute name="extraClassForDropdown" type="java.lang.String" %>
<%@ attribute name="extraClassForOptionWrapper" type="java.lang.String" %>

<c:set var="defaultTimeInString" value="${defaultTime < 10 ? '0' : ''}${defaultTime}"/>


<div class="k-dropdown ${extraClassForDropdown}">
    <div class="k-input dropdown__select-wrapper">
        <input id="${dropdownInputId}" type="hidden" value="${defaultTimeInString}" name="">
        <span>${defaultTimeInString}</span>
        <i class="arrow down"></i>
    </div>
    <div class="dropdown__option-wrapper dropdown__option-box ${extraClassForOptionWrapper}">
        <c:forEach var="time" begin="0" end="${maximumTime}">
            <c:set var="timeInString" value="${time < 10 ? '0' : ''}${time}"/>
            <button type="button" data-id="${timeInString}" class="dropdown__item dropdown__option"
                    role="option" aria-selected="${timeInString == defaultTimeInString}">
                <div>
                    <span class="dropdown__item__text">${timeInString}</span>
                </div>
            </button>
        </c:forEach>
    </div>
</div>