<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute required="true" name="label" type="java.lang.String" %>
<%@ attribute required="true" name="value" type="java.lang.Boolean" %>
<%@ attribute required="true" name="fieldName" type="java.lang.String" %>



<div class="content__white-box">
    <div class="white-box__label-wrapper">
        <label>${label}</label>
    </div>
    <div class="white-box__right-side white-box__checkbox-wrapper">
        <label class="switch">
            <input name="${fieldName}" type="checkbox" value="${value}" ${value ? 'checked' : ''}>
            <span class="slider round"></span>
        </label>
        <label class="checkbox-label">
            ${value}
        </label>
    </div>
</div>