<%@ tag description="Main Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute required="true" name="directory" type="java.lang.String" %>
<%@ attribute name="imageList" type="java.util.List" %>


<div>
    <div class="image-gallery__tool-bar">
        <input id="image-gallery-input" type="file" class="hide">
        <button id="image-gallery-add-btn" type="button" class="link-primary k-btn tool-bar__add-link img-hover-btn">
            <img class="icon-medium" src="${pageContext.request.contextPath}/resources/image/plus-primary.png">
            <img class="icon-medium" src="${pageContext.request.contextPath}/resources/image/plus-primary-darker.png">
            <span>추가하기</span>
        </button>
        <button id="image-gallery-delete-btn" type="button" class="link-primary k-btn tool-bar__add-link img-hover-btn">
            <img class="icon-medium" src="${pageContext.request.contextPath}/resources/image/bin-primary.png">
            <img class="icon-medium" src="${pageContext.request.contextPath}/resources/image/bin-primary-darker.png">
            <span>삭제하기</span>
        </button>
    </div>
    <div class="image-gallery__drop-box" data-directory="${directory}">
        <%--        <div class="image-gallery__drop-logo-wrapper">--%>
        <%--            <img src="${pageContext.request.contextPath}/resources/image/upload.png"/>--%>
        <%--            <img src="${pageContext.request.contextPath}/resources/image/upload-primary.png"/>--%>
        <%--            <span>업로드할 사진을 드롭하세요</span>--%>
        <%--        </div>--%>
        <ul class="image-gallery__image-list">

            <c:forEach var="image" items="${imageList}">
                <li class="image-gallery__item hide processing">
                    <div class="image-gallery__image-wrapper">
                        <div class="lds-ellipsis">
                            <div></div>
                            <div></div>
                            <div></div>
                            <div></div>
                        </div>
                        <div class="screen-filter"></div>
                        <div class="image-gallery__check-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/resources/image/round-check-primary.png"
                                 class="image-gallery__check-icon"/>
                        </div>
                        <div class="image-gallery__caption">${image.name}</div>
                        <img src="${image.path}" class="source-image"/>
                        <input type="hidden" name="baseImageVmList[0].name" value="${image.name}">
                        <input type="hidden" name="baseImageVmList[0].fileType" value="${image.fileType}">
                        <input type="hidden" name="baseImageVmList[0].repImage" value="${image.repImage}">
                    </div>
                    <div class="image-gallery__error-wrapper image-gallery__upload-error-wrapper hide">
                        <img src="${pageContext.request.contextPath}/resources/image/image.png">
                        <p>업로드를 실패했습니다.</p>
                        <button type="button" class="image-gallery__upload-btn k-btn btn-bold image-gallery__btn">
                            재업로드
                        </button>
                    </div>
                    <div class="image-gallery__error-wrapper image-gallery__download-error-wrapper hide">
                        <img src="${pageContext.request.contextPath}/resources/image/image.png">
                        <p>다운로드를 실패했습니다.</p>
                        <button type="button" class="image-gallery__upload-btn k-btn btn-bold image-gallery__btn">
                            다운로드
                        </button>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>

</div>
</div>

<%--<li class="image-gallery__item processing">--%>
    <%--<div class="image-gallery__image-wrapper">--%>
        <%--<div class="lds-ellipsis">--%>
            <%--<div></div>--%>
            <%--<div></div>--%>
            <%--<div></div>--%>
            <%--<div></div>--%>
        <%--</div>--%>
        <%--<div class="screen-filter"></div>--%>
        <%--<div class="image-gallery__check-icon-wrapper">--%>
            <%--<img src="${pageContext.request.contextPath}/resources/image/round-check-primary.png"--%>
                 <%--class="image-gallery__check-icon"/>--%>
        <%--</div>--%>
        <%--<div class="image-gallery__caption">TESTIMAGE.jpg</div>--%>
        <%--<img src="${pageContext.request.contextPath}/resources/image/Gyeongbokgung.jpg"--%>
             <%--class="source-image"--%>
             <%--data-file-name="TESTIMAGE.jpg"--%>
             <%--onload="imageOnLoad(this)"--%>
             <%--onerror="imageOnError(this)"/>--%>
    <%--</div>--%>
    <%--<div class="image-gallery__error-wrapper image-gallery__upload-error-wrapper hide">--%>
        <%--<img src="${pageContext.request.contextPath}/resources/image/image.png">--%>
        <%--<p>업로드를 실패했습니다.</p>--%>
        <%--<button type="button" class="image-gallery__upload-btn k-btn btn-bold image-gallery__btn">--%>
            <%--재업로드--%>
        <%--</button>--%>
    <%--</div>--%>
    <%--<div class="image-gallery__error-wrapper image-gallery__download-error-wrapper hide">--%>
        <%--<img src="${pageContext.request.contextPath}/resources/image/image.png">--%>
        <%--<p>다운로드를 실패했습니다.</p>--%>
        <%--<button type="button" class="image-gallery__download-btn k-btn btn-bold image-gallery__btn">--%>
            <%--다운로드--%>
        <%--</button>--%>
    <%--</div>--%>
<%--</li>--%>