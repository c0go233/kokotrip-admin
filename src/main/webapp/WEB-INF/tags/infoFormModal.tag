<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute required="true" name="ownerId" type="java.lang.Integer" %>
<%@ attribute required="true" name="ownerFieldName" type="java.lang.String" %>
<%@ attribute required="true" name="ownerTypeLabel" type="java.lang.String" %>
<%@ attribute required="true" name="nameLabel" type="java.lang.String" %>
<%@ attribute required="true" name="descriptionLabel" type="java.lang.String" %>
<%@ attribute required="true" name="urlPrefix" type="java.lang.String" %>
<%@ attribute required="true" name="includeDescription" type="java.lang.Boolean" %>
<%@ attribute required="true" name="supportLanguageLinkedHashMap" type="java.util.LinkedHashMap" %>


<div class="modal fade" id="info-form-modal" tabindex="-1" role="dialog"
     aria-labelledby="info-form-modal-title" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">${ownerTypeLabel} 번역폼</h5>
                <button type="button" class="k-btn" data-dismiss="modal" aria-label="Close">
                    <img src="${pageContext.request.contextPath}/resources/image/close.png" class="icon-small"/>
                </button>
            </div>
            <div class="modal-body info-modal__body">

                <form id="info-form" class="info-form">

                    <span id="info-error-exception" class="error modal-exception"></span>

                    <input type="hidden" name="${ownerFieldName}" value="${ownerId}">
                    <input type="hidden" id="info-id-input" name="id"/>

                    <t:contentWhiteBoxDropdown label="번역언어" key="1" fieldName="supportLanguageId"
                                               items="${supportLanguageLinkedHashMap}"
                                               id="info-support-language-select"/>

                    <t:contentWhiteBoxInput label="${nameLabel}">
                        <jsp:attribute name="input">
                            <input id="info-name-input" name="name" type="text" class="k-input"/>
                            <span id="info-error-name" class="field-error"></span>
                        </jsp:attribute>
                    </t:contentWhiteBoxInput>

                    <c:if test="${includeDescription}">
                        <t:contentWhiteBoxInput label="${descriptionLabel}">
                        <jsp:attribute name="input">
                            <textarea id="info-description-textarea" name="description" type="text"
                                      class="k-input k-textarea"></textarea>
                            <span id="info-error-description" class="field-error"></span>
                        </jsp:attribute>
                        </t:contentWhiteBoxInput>
                    </c:if>

                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn-light btn-large btn" data-dismiss="modal">닫기</button>
                <button type="button" class="btn-bold btn-large btn" id="info-form-submit-btn"
                        data-url-prefix="${pageContext.request.contextPath}${urlPrefix}">저장하기
                </button>
            </div>

        </div>
    </div>
</div>