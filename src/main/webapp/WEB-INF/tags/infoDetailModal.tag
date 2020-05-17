<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute required="true" name="ownerName" type="java.lang.String" %>
<%@ attribute required="true" name="nameLabel" type="java.lang.String" %>
<%@ attribute required="true" name="descriptionLabel" type="java.lang.String" %>
<%@ attribute required="true" name="includeDescription" type="java.lang.Boolean" %>
<%@ attribute required="true" name="includeTag" type="java.lang.Boolean" %>


<div class="modal fade" id="info-detail-modal" tabindex="-1" role="dialog"
     aria-labelledby="info-detail-modal-title" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="info-detail-modal-title">${ownerName} 번역 상세보기</h5>
                <button type="button" class="k-btn" data-dismiss="modal" aria-label="Close">
                    <img src="${pageContext.request.contextPath}/resources/image/close.png" class="icon-small"/>
                </button>
            </div>
            <div class="modal-body info-modal__body info-detail-modal__body">



                <div class="content__white-box">
                    <div class="white-box__label-wrapper">
                        <label>아이디</label>
                    </div>
                    <div class="white-box__right-side display-flex_align-center">
                        <span id="info-detail-id-span"></span>
                    </div>
                </div>

                <%--<t:contentWhiteBox label="아이디" value="" rightSideSpanId="info-detail-id-span"/>--%>

                <div class="content__white-box">
                    <div class="white-box__label-wrapper">
                        <label>${nameLabel}</label>
                    </div>
                    <div class="white-box__right-side display-flex_align-center">
                        <span id="info-detail-name-span"></span>
                    </div>
                </div>
                <%--<t:contentWhiteBox label="${nameLabel}" value="" rightSideSpanId="info-detail-name-span"/>--%>

                <div class="content__white-box">
                    <div class="white-box__label-wrapper">
                        <label>번역언어</label>
                    </div>
                    <div class="white-box__right-side display-flex_align-center">
                        <span id="info-detail-support-language-name-span"></span>
                    </div>
                </div>
                <%--<t:contentWhiteBox label="번역언어" value="" rightSideSpanId="info-detail-support-language-name-span"/>--%>



                <c:if test="${includeDescription}">
                    <div class="content__white-box">
                        <div class="white-box__label-wrapper">
                            <label>${descriptionLabel}</label>
                        </div>
                        <div class="white-box__right-side display-flex_align-center">
                            <span id="info-detail-description-span"></span>
                        </div>
                    </div>
                </c:if>



                <c:if test="${includeTag}">
                    <div class="content__white-box">
                        <div class="white-box__label-wrapper">
                            <label>하위분류</label>
                        </div>
                        <div class="white-box__right-side display-flex_align-center">
                            <span id="info-detail-tag-span"></span>
                        </div>
                    </div>
                </c:if>
                <%--<t:contentWhiteBox label="하위분류" value="" rightSideSpanId="info-detail-tag-span"/>--%>


                <div class="content__white-box">
                    <div class="white-box__label-wrapper">
                        <label>생성일</label>
                    </div>
                    <div class="white-box__right-side display-flex_align-center">
                        <span id="info-detail-created-at-span"></span>
                    </div>
                </div>
                <%--<t:contentWhiteBox label="생성일" value="" rightSideSpanId="info-detail-created-at-span"/>--%>

                <div class="content__white-box">
                    <div class="white-box__label-wrapper">
                        <label>편집일</label>
                    </div>
                    <div class="white-box__right-side display-flex_align-center">
                        <span id="info-detail-updated-at-span"></span>
                    </div>
                </div>
                <%--<t:contentWhiteBox label="편집일" value="" rightSideSpanId="info-detail-updated-at-span"/>--%>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn-bold btn-large btn info-detail-modal__close-btn" data-dismiss="modal">닫기</button>
            </div>

        </div>
    </div>
</div>