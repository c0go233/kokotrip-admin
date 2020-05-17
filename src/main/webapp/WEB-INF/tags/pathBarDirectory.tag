<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute required="true" name="url" type="java.lang.String" %>
<%@ attribute required="true" name="name" type="java.lang.String" %>


<a href="${pageContext.request.contextPath}${url}" class="link-primary">${name}</a>
<img src="${pageContext.request.contextPath}/resources/image/next.png">
