<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute required="true" name="input" fragment="true" %>
<%@ attribute required="true" name="label" type="java.lang.String" %>
<%@ attribute required="true" name="url" type="java.lang.String" %>
<%@ attribute required="true" name="valueForSelect" type="java.lang.String" %>
<%@ attribute required="true" name="placeHolder" type="java.lang.String" %>
<%@ attribute name="classForOptionWrapper" type="java.lang.String" %>
<%@ attribute name="idForUnselectBtn" type="java.lang.String" %>




<div class="content__white-box">
    <div class="white-box__label-wrapper">
        <label>${label}</label>
    </div>

    <div class="white-box__right-side white-box__dropdown-wrapper">
        <div class="auto-complete-wrapper">
            <jsp:invoke fragment="input" />
            <div class="auto-complete__select-wrapper ${empty valueForSelect ? '' : 'auto-complete_selected'}">
                <input type="text"
                       class="k-input auto-complete-select"
                       value="${valueForSelect}"
                       placeholder="${placeHolder}"
                       data-url="${url}"
                       ${empty valueForSelect ? '' : 'disabled'}>

                <button id="${idForUnselectBtn}" type="button" class="btn-close auto-complete__unselect-btn"></button>
            </div>

            <div class="dropdown__option-box auto-complete__option-wrapper ${classForOptionWrapper}">
            </div>
        </div>
    </div>
</div>