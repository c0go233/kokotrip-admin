<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8"%>

<%@ attribute required="true" name="includeDescription" type="java.lang.Boolean" %>
<%@ attribute required="true" name="includeOrder" type="java.lang.Boolean" %>
<%@ attribute required="true" name="nameLabel" type="java.lang.String" %>
<%@ attribute required="true" name="datatableId" type="java.lang.String" %>

<%@ attribute name="descriptionLabel" type="java.lang.String" %>
<%@ attribute name="dataList" type="java.util.List" %>
<%@ attribute name="urlPrefix" type="java.lang.String" %>





<table id="${datatableId}" class="k-table border-box">
    <thead>
    <tr>
        <th><span>아이디</span><div class="diamond"></div></th>
        <th><span>${nameLabel}</span><div class="diamond"></div></th>
        <c:if test="${includeDescription}">
            <th><span>${descriptionLabel}</span><div class="diamond"></div></th>
        </c:if>
        <c:if test="${includeOrder}">
            <th><span>순서</span><div class="diamond"></div></th>
        </c:if>
        <th><span>활성화</span><div class="diamond"></div></th>
        <th>편집</th>
        <th>상세</th>
    </tr>
    </thead>

    <c:if test="${dataList != null}">
        <tbody>
        <c:forEach var="data" items="${dataList}">
            <tr>
                <td>${data.id}</td>
                <td>${data.name}</td>
                <c:if test="${includeDescription}">
                    <td>${data.description}</td>
                </c:if>
                <c:if test="${includeOrder}">
                    <td>${data.order}</td>
                </c:if>
                <td>${data.enabled}</td>
                <td><a href="${pageContext.request.contextPath}${urlPrefix}/edit/${data.id}" class="link-primary">편집</a></td>
                <td><a href="${pageContext.request.contextPath}${urlPrefix}/detail/${data.id}" class="link-primary">상세</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </c:if>
</table>




