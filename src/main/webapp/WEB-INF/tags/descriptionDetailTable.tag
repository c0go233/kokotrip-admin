<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8"%>

<%--<%@ attribute required="true" name="descriptionViewModel" type="com.kokotripadmin.viewmodel.common.DescriptionVm" %>--%>
<%@ attribute required="true" name="descriptionViewModel" type="java.lang.Object" %>


<table>
    <tbody>

    <tr>
        <td>아이디</td>
        <td>${descriptionViewModel.id}</td>
    </tr>
    <tr>
        <td>머리말</td>
        <td>${descriptionViewModel.name}</td>
    </tr>
    <tr>
        <td>본문</td>
        <td>${descriptionViewModel.description}</td>
    </tr>
    <tr>
        <td>순서</td>
        <td>${descriptionViewModel.order}</td>
    </tr>
    <tr>
        <td>팝업</td>
        <td>${descriptionViewModel.popup}</td>
    </tr>
    <tr>
        <td>활성화</td>
        <td>${descriptionViewModel.enabled}</td>
    </tr>
    <tr>
        <td>생성일</td>
        <td>${descriptionViewModel.createdAt}</td>
    </tr>
    <tr>
        <td>편집일</td>
        <td>${descriptionViewModel.updatedAt}</td>
    </tr>

    </tbody>
</table>