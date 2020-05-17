<%--
  Created by IntelliJ IDEA.
  User: mtae
  Date: 26/03/2020
  Time: 9:33 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>





<div class="modal fade" id="info-form-modal" tabindex="-1" role="dialog"
     aria-labelledby="info-form-modal-title" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="info-form-modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                <div>
                    <span id="info-error-exception"></span>
                </div>


                <form id="info-form">

                    <table>
                        <tbody>
                        <tr>
                            <td>번역언어:</td>
                            <td>
                                <select id="info-support-language-select" name="supportLanguageId">
                                    <c:forEach var="supportLanguage" items="${requestScope.supportLanguageHashMap}">
                                        <option value="${supportLanguage.key}">${supportLanguage.value}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><span id="info-name-label"></span>:</td>
                            <td><input id="info-name-input" name="name" type="text"/></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><span id="info-error-name" class="error"></span></td>
                        </tr>
                        <tr>
                            <td><span id="info-description-label"></span>:</td>
                            <td><textarea id="info-description-textarea" name="description" form="info-form"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><span id="info-error-description" class="error"></span></td>
                        </tr>
                        <tr>
                            <td>팝업:</td>
                            <td><input id="info-popup-checkbox" name="popup" type="checkbox"/></td>
                        </tr>
                        </tbody>
                    </table>

                    <%--<input id="info-url-prefix-input" type="hidden" value="${param.urlPrefix}" disabled>--%>
                    <input id="ownerId" type="hidden"/>
                    <input id="info-id-input" name="id" type="hidden"/>

                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                <button type="button" class="btn btn-primary" id="info-form-submit-btn">저장하기</button>
            </div>

        </div>
    </div>
</div>