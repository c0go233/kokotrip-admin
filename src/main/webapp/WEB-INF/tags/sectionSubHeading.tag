<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute required="true" name="subHeading" type="java.lang.String" %>
<%@ attribute required="true" name="subHeadingLabel" type="java.lang.String" %>



<div>
    <h2 class="sub-heading">${subHeading}</h2>
    <div class="sub-heading-label-wrapper">
        <label>${subHeadingLabel}</label>
    </div>
</div>