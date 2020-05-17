<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute required="true" name="ownerId" type="java.lang.Integer" %>
<%@ attribute required="true" name="ownerName" type="java.lang.String" %>
<%@ attribute required="true" name="ownerLabel" type="java.lang.String" %>
<%@ attribute required="true" name="urlPrefix" type="java.lang.String" %>
<%@ attribute required="true" name="queryString" type="java.lang.String" %>
<%@ attribute required="true" name="descriptionList" type="java.util.List" %>



<a href="${urlPrefix}/description/add?${queryString}" class="btn btn-primary">${ownerLabel} 설명 추가하기</a>

<br/>
<br/>

<table id="description-table" class="table table-striped">
    <thead>
    <tr>
        <th>아이디</th>
        <th>머리말</th>
        <th>본문</th>
        <th>순서</th>
        <th>활성화</th>
        <th>편집</th>
        <th>상세</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="description" items="${descriptionList}">

        <tr role="row">
            <td>${description.id}</td>
            <td>${description.name}</td>
            <td>${description.description}</td>
            <td>${description.order}</td>
            <td>${description.enabled}</td>
            <td>
                <a href="${urlPrefix}/description/edit/${description.id}" class="btn btn-primary">편집</a>
            </td>
            <td>
                <a href="${urlPrefix}/description/detail/${description.id}" class="btn btn-primary">상세</a>
            </td>
        </tr>

    </c:forEach>
    </tbody>
</table>
