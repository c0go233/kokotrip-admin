<%@ tag description="Main Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute required="true" name="selectedKey" type="java.lang.Integer" %>
<%@ attribute required="true" name="optionLinkedHashMap" type="java.util.LinkedHashMap" %>
<%@ attribute required="true" name="dropdownInputId" type="java.lang.String" %>
<%@ attribute name="dropdownInputName" type="java.lang.String" %>
<%@ attribute name="extraClassForDropdown" type="java.lang.String" %>
<%@ attribute name="extraClassForOptionWrapper" type="java.lang.String" %>



<div class="k-dropdown ${extraClassForDropdown}">
    <div class="k-input dropdown__select-wrapper">
        <input id="${dropdownInputId}" type="hidden"
               value="${selectedKey}" name="${dropdownInputName}">
        <span>${optionLinkedHashMap.get(selectedKey)}</span>
        <i class="arrow down"></i>
    </div>
    <div class="dropdown__option-wrapper dropdown__option-box ${extraClassForOptionWrapper}">
        <c:forEach var="option" items="${optionLinkedHashMap}">
            <button type="button" data-id="${option.key}" class="dropdown__item dropdown__option" role="option"
                    aria-selected="${option.key == selectedKey}">
                <div>
                    <span class="dropdown__item__text">${option.value}</span>
                </div>
            </button>
        </c:forEach>
    </div>
</div>