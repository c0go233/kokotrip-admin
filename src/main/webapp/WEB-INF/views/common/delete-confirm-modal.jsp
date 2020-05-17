<%@ page contentType="text/html;charset=UTF-8" language="java" %>





<div id="delete-confirm-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"><span id="delete-confirm-modal-title-prefix"></span> 삭제하기</h5>
                <button type="button" class="close k-btn" data-dismiss="modal" aria-label="Close">
<%--                    <span aria-hidden="true">&times;</span>--%>
                    <img src="${pageContext.request.contextPath}/resources/image/close.png" class="icon-small"/>
                </button>
            </div>
            <div class="modal-body delete-confirm-modal-body">
                <div><span id="delete-confirm-modal-statement"></span> 을(를) 삭제 하시겠습니까?</div>
                <span id="delete-confirm-modal-error-exception" class="error"></span>
            </div>
            <div class="modal-footer">
                <button type="button" id="delete-confirm-btn" class="btn-delete btn-large btn">삭제하기</button>
                <button type="button" class="btn-bold btn-large btn " data-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>
