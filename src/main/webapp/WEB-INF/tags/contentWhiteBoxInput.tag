<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute required="true" name="input" fragment="true" %>
<%@ attribute required="true" name="label" type="java.lang.String" %>







<div class="content__white-box">
    <div class="white-box__label-wrapper">
        <label>${label}</label>
    </div>

    <div class="white-box__right-side white-box__input-wrapper">
        <jsp:invoke fragment="input" />
    </div>
</div>