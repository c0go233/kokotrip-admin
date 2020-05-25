<%@ tag description="Main Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute required="true" name="prefixUrl" type="java.lang.String" %>
<%@ attribute required="true" name="ownerIdName" type="java.lang.String" %>
<%@ attribute required="true" name="ownerId" type="java.lang.String" %>
<%@ attribute name="imageList" type="java.util.List" %>


<div class="section__content border-box">
    <div class="image-gallery__tool-bar">
        <input id="image-gallery-file-input" type="file" class="hide">
        <button id="image-gallery-add-btn" type="button" class="link-primary k-btn tool-bar__add-link img-hover-btn">
            <img class="icon-medium" src="${pageContext.request.contextPath}/resources/image/plus-primary.png">
            <img class="icon-medium" src="${pageContext.request.contextPath}/resources/image/plus-primary-darker.png">
            <span>추가하기</span>
        </button>
        <div class="image-gallery__tool-bar__right-side">
            <button id="image-gallery-representative-image-btn" type="button"
                    class="image-gallery__representative-btn link-primary k-btn tool-bar__add-link ">
                <div></div>
                <span>대표이미지</span>
            </button>
            <%--<button id="image-gallery-delete-btn"--%>
                    <%--type="button"--%>
                    <%--class="link-primary k-btn tool-bar__add-link img-hover-btn"--%>
                    <%--data-delete-url="${pageContext.request.contextPath}${prefixUrl}/delete">--%>
                <%--<img class="icon-medium" src="${pageContext.request.contextPath}/resources/image/bin-primary.png">--%>
                <%--<img class="icon-medium"--%>
                     <%--src="${pageContext.request.contextPath}/resources/image/bin-primary-darker.png">--%>
                <%--<span>삭제하기</span>--%>
            <%--</button>--%>
        </div>

    </div>
    <div class="image-gallery__drop-box"
         data-prefix-url="${prefixUrl}"
         data-owner-id-name="${ownerIdName}"
         data-owner-id="${ownerId}">
        <div class="image-gallery__drop-logo-wrapper">
            <img src="${pageContext.request.contextPath}/resources/image/upload.png"/>
            <span>업로드할 사진을 드롭하세요</span>
        </div>
        <ul class="image-gallery__image-list">

            <c:forEach var="image" items="${imageList}">
                <li class="image-gallery__item ${image.repImage ? 'rep-image' : ''} processing"
                    data-image-id="${image.id}"
                    data-image-order="${image.order}">
                    <div class="image-gallery__image-wrapper">
                        <div class="lds-ellipsis">
                            <div></div>
                            <div></div>
                            <div></div>
                            <div></div>
                        </div>
                        <div class="screen-filter"></div>
                        <button class="btn-close image-gallery__close-btn"></button>
                        <div class="image-gallery__check-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/resources/image/round-check-primary.png"
                                 class="image-gallery__check-icon"/>
                        </div>
                        <div class="image-gallery__caption">${image.name}</div>
                        <img src="${image.url}"
                             class="source-image"
                             onload="imageOnLoad(this)"
                             onerror="imageOnError(this)"/>
                    </div>
                    <div class="image-gallery__error-wrapper image-gallery__upload-error-wrapper hide">
                        <button class="btn-close image-gallery__close-btn"></button>
                        <img src="${pageContext.request.contextPath}/resources/image/image.png">
                        <p>업로드를 실패했습니다.</p>
                        <button type="button" class="image-gallery__upload-btn k-btn btn-bold image-gallery__btn">
                            재업로드
                        </button>
                    </div>
                    <div class="image-gallery__error-wrapper image-gallery__download-error-wrapper hide">
                        <button class="btn-close image-gallery__close-btn"></button>
                        <img src="${pageContext.request.contextPath}/resources/image/image.png">
                        <p>다운로드를 실패했습니다.</p>
                        <button type="button" class="image-gallery__download-btn k-btn btn-bold image-gallery__btn">
                            다운로드
                        </button>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>

</div>

<div class="content__footer">
    <button id="image-gallery__save-btn" class="k-btn btn-bold btn-large">저장하기</button>
</div>


<script>

    function imageOnLoad (target) {
        let imageItem = target.parentNode.parentNode;
        imageItem.classList.remove('processing');
        showImageItemWithJavascript(imageItem, 'image-gallery__image-wrapper');
    }

    function imageOnError (target) {
        let imageItem = target.parentNode.parentNode;
        imageItem.classList.remove('processing');
        showImageItemWithJavascript(imageItem, 'image-gallery__download-error-wrapper');
    }

    function showImageItemWithJavascript(imageItem, classToShow) {

        let childNodes = imageItem.childNodes;
        for (let count = 0; count < imageItem.childNodes.length; count++) {
            let childNode = childNodes[count];
            if (childNode.tagName === 'DIV') {
                if (childNode.classList.contains(classToShow)) childNode.classList.remove('hide');
                else childNode.classList.add('hide');
            }
        }
    }

</script>


