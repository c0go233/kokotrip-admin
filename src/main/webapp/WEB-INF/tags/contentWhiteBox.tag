<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute required="true" name="label" type="java.lang.String" %>
<%@ attribute required="true" name="value" type="java.lang.String" %>
<%@ attribute name="classForWhiteBox" type="java.lang.String" %>
<%@ attribute name="classForLabelWrapper" type="java.lang.String" %>





<div class="${classForWhiteBox} content__white-box">
    <div class="${classForLabelWrapper} white-box__label-wrapper">
        <label>${label}</label>
    </div>
    <div class="white-box__right-side display-flex_align-center">
        <span>${value}</span>
    </div>
</div>