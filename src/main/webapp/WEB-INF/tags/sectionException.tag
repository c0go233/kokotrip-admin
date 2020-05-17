<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute required="true" name="exceptionMessage" type="java.lang.String" %>


<c:set var="exceptionDisplay" value="${exceptionMessage.trim().length() == 0 ? 'hide' : ''}" />

<div class="${exceptionDisplay} section__exception">
    <%--<i class="fas fa-exclamation-circle error"></i>--%>
    <span class="error">${exceptionMessage}</span>
</div>