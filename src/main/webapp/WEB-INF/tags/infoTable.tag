<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8"%>

<%@ attribute required="true" name="infoList" type="java.util.List" %>
<%@ attribute required="true" name="nameLabel" type="java.lang.String" %>
<%@ attribute required="true" name="descriptionLabel" type="java.lang.String" %>
<%@ attribute required="true" name="deleteUrl" type="java.lang.String" %>
<%@ attribute required="true" name="supportLanguageLinkedHashMap" type="java.util.LinkedHashMap" %>








<table id="info-data-table" class="k-table">
    <thead>
    <tr>
        <th><span>아이디</span><div class="diamond"></div></th>
        <th><span>번역언어</span><div class="diamond"></div></th>
        <th><span>${nameLabel}</span><div class="diamond"></div></th>
        <th><span>${descriptionLabel}</span><div class="diamond"></div></th>
        <th>편집</th>
        <th>상세</th>
        <th>삭제</th>
        <th>지원언어아이디</th>
        <th>태그</th>
        <th>생성일</th>
        <th>편집일</th>
    </tr>
    </thead>
    <tbody id="info-table-tbody" data-delete-url="${pageContext.request.contextPath}${deleteUrl}" data-success-callback="removeInfoRow">
    <c:forEach var="info" items="${infoList}">

        <tr role="row" data-info-id="${info.id}">
            <td>${info.id}</td>
            <td>${info.supportLanguageName}</td>
            <td>${info.name}</td>
            <td>${info.description}</td>
            <td>
                <button type="button" class="btn-light btn-small k-btn info-row__edit-btn">편집</button>
            </td>
            <td>
                <button type="button" class="btn-light btn-small k-btn info-row__detail-btn">상세</button>
            </td>
            <td>
                <button type="button" class="btn-delete btn-small k-btn info-row__delete-btn">삭제</button>
            </td>
            <td>${info.supportLanguageId}</td>
            <td>${info.tagName}</td>
            <td>${info.createdAt}</td>
            <td>${info.updatedAt}</td>
        </tr>

    </c:forEach>
    </tbody>
</table>
