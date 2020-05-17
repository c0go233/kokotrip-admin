<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute required="true" name="heading" type="java.lang.String" %>
<%@ attribute required="true" name="headingLabel" type="java.lang.String" %>





<div class="section__heading">
    <h1 class="heading">${heading}</h1>
    <p>${headingLabel}</p>
</div>