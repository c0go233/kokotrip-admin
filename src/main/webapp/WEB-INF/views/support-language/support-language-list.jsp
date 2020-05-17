<%--
  Created by IntelliJ IDEA.
  User: kisungtae
  Date: 2020-03-07
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle" value="번역언어 리스트"/>


<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>

<t:masterPage>

    <jsp:attribute name="sideNav">
        <t:sideNav supportLanguage="true"/>
    </jsp:attribute>

    <jsp:body>


        <div class="path-bar">
            <span>${pageTitle}</span>
        </div>

        <div class="section__heading">
            <h1 class="heading">${pageTitle}</h1>
            <p>번역(飜譯, translate)은 어떤 언어로 쓰인 글을 다른 언어로 된 상응하는 의미의 글로 전달하는 일이다. 이 때 전자의 언어를 원어 또는 출발어(source language)라 하고,
                후자의 언어를 번역어 또는 도착어(target language)라고 한다.</p>
        </div>

        <table id="support-language-data-table" class="k-table">
            <thead>
            <tr>
                <th><span>아이디</span><div class="diamond"></div></th>
                <th><span>언어명</span><div class="diamond"></div></th>
                <th><span>언어표시명</span><div class="diamond"></div></th>
                <th><span>순서</span><div class="diamond"></div></th>
                <th><span>활성화</span><div class="diamond"></div></th>
                <th><span>편집</span></th>
                <th><span>삭제</span></th>
            </tr>
            </thead>
            <tbody id="support-language-data-table__tbody">
            <c:forEach var="supportLanguage" items="${supportLanguageList}">
                <tr data-info-id="${supportLanguage.id}">
                    <td>${supportLanguage.id}</td>
                    <td>${supportLanguage.name}</td>
                    <td>${supportLanguage.displayName}</td>
                    <td>${supportLanguage.order}</td>
                    <td>${supportLanguage.enabled ? 'On' : 'Off'}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/support-language/edit/${supportLanguage.id}" class="link-primary">편집</a>
                    </td>
                    <td>
                        <button type="button" class="btn-delete btn-small k-btn info-row__delete-btn">삭제</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>


        <jsp:include page="/WEB-INF/views/common/delete-confirm-modal.jsp" />

    </jsp:body>

</t:masterPage>


<script>


    $(document).ready(function () {
        let supportLanguageTable = $('table#support-language-data-table');
        let dataTableSetting = getBasicDataTableSetting(false, 5);
        dataTableSetting.columnDefs = [{ targets: 5, searchable: false, orderable: false },
                                       { targets: 6, searchable: false, orderable: false }];

        eInfoDataTable = supportLanguageTable.DataTable(dataTableSetting);
        appendAddLinkToDataTableToolbar(supportLanguageTable, '${pageContext.request.contextPath}/support-language/add');

        eInfoTableTbody = $('tbody#support-language-data-table__tbody');
        eInfoTableTbody.on(eventType.click, 'button.info-row__delete-btn', function () {
            let data = eInfoDataTable.row($(this).closest('tr')).data();
            openDeleteConfirmModal(data[infoCellIndex.id],
                data[infoCellIndex.name],
                '번역언어',
                '${pageContext.request.contextPath}/support-language/delete',
                'removeInfoRow');
        });
        setDeleteConfirmModal();

    });

</script>

</body>
</html>
