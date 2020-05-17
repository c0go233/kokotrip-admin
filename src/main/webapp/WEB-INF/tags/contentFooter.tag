<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute required="true" name="id" type="java.lang.Integer" %>
<%@ attribute required="true" name="name" type="java.lang.String" %>
<%@ attribute required="true" name="urlPrefix" type="java.lang.String" %>
<%@ attribute required="true" name="type" type="java.lang.String" %>






<div class="content__footer">
    <button id="delete-btn" type="submit" class="btn-delete btn-large k-btn" data-name="${name}"
            data-delete-url="${pageContext.request.contextPath}${urlPrefix}/delete" data-id="${id}"
            data-type="${type}" data-success-callback="redirectToUrl">삭제하기</button>
    <a href="${pageContext.request.contextPath}${urlPrefix}/edit/${id}" class="btn-bold btn-large k-btn">편집하기</a>
</div>