<%@ tag description="Main Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ attribute required="true" name="ticketTypeLinkedHashMap" type="java.util.LinkedHashMap" %>
<%@ attribute required="true" name="ticketPriceList" type="java.util.List" %>


<div class="timetable-builder-wrapper ticket-price-builder-wrapper">

    <div class="timetable-builder__item">
        <span class="timetable-builder__heading">티켓타입</span>

        <div class="k-dropdown timetable-builder__medium-dropdown">
            <div class="k-input dropdown__select-wrapper">
                <input id="ticket-price-builder-ticket-type-input" type="hidden"
                       value="${ticketTypeLinkedHashMap.entrySet().iterator().next().key}">
                <span>${ticketTypeLinkedHashMap.entrySet().iterator().next().value}</span>
                <i class="arrow down"></i>
            </div>
            <div class="dropdown__option-wrapper dropdown__option-box">
                <c:forEach varStatus="loop" var="option" items="${ticketTypeLinkedHashMap}">
                    <button type="button" data-id="${option.key}" class="dropdown__item dropdown__option" role="option"
                            aria-selected="${loop.index == 0}">
                        <div>
                            <span class="dropdown__item__text">${option.value}</span>
                        </div>
                    </button>
                </c:forEach>
            </div>
        </div>
    </div>

    <div class="timetable-builder__item">
        <span class="timetable-builder__heading">가격</span>
        <span class="input-currency-won"><input type="number" id="ticket-price-builder-ticket-price-input" class="k-input ticket-price-builder__input"></span>
    </div>

    <div class="timetable-builder__item">
        <span class="timetable-builder__heading">대표가격</span>

        <div class="ticket-price-builder__checkbox-wrapper">
            <label class="switch">
                <input id="ticket-price-builder-rep-price-checkbox" name="repPrice" type="checkbox" value="true" checked>
                <span class="slider round"></span>
            </label>
            <label class="checkbox-label">On</label>
        </div>
    </div>

    <div class="timetable-builder__item">
        <span class="timetable-builder__heading timetable-builder__empty-label"></span>
        <div>
            <button id="ticket-price-builder-add-btn" type="button" class="btn-bold btn timetable-builder__btn">추가하기</button>
        </div>
    </div>


</div>


<div id="ticket-price-wrapper">


    <c:forEach var="ticketPrice" items="${ticketPriceList}">
        <div role="row"
             class="timetable-builder-wrapper ticket-price-builder-wrapper">
            <div class="timetable-builder__item">
                <div class="k-dropdown timetable-builder__medium-dropdown">
                    <div class="k-input dropdown__select-wrapper">
                        <input name="ticketPriceVmList[0].ticketTypeId" type="hidden" value="${ticketPrice.ticketTypeId}">
                        <span>${ticketTypeLinkedHashMap.get(ticketPrice.ticketTypeId)}</span>
                        <i class="arrow down"></i>
                    </div>
                    <div class="dropdown__option-wrapper dropdown__option-box">
                        <c:forEach var="option" items="${ticketTypeLinkedHashMap}">
                            <button type="button" data-id="${option.key}" class="dropdown__item dropdown__option" role="option"
                                    aria-selected="${option.key == ticketPrice.ticketTypeId}">
                                <div>
                                    <span class="dropdown__item__text">${option.value}</span>
                                </div>
                            </button>
                        </c:forEach>
                    </div>
                </div>

            </div>

            <div class="timetable-builder__item">
                <span class="input-currency-won">
                    <input type="number" name="ticketPriceVmList[0].price" class="k-input ticket-price-builder__input" value="${ticketPrice.price}">
                </span>
            </div>

            <div class="timetable-builder__item">
                <div class="ticket-price-builder__checkbox-wrapper">
                    <label class="switch">
                        <input name="ticketPriceVmList[0].repPrice" type="checkbox" value="${ticketPrice.repPrice}" ${ticketPrice.repPrice ? 'chcked' : ''}>
                        <span class="slider round"></span>
                    </label>
                    <label class="checkbox-label">${ticketPrice.repPrice ? 'On' : 'Off'}</label>
                </div>
            </div>

            <div class="timetable-builder__item">
                <button type="button" class="timetable-builder__btn timetable-builder__delete-btn btn-close"></button>
            </div>
        </div>
    </c:forEach>
</div>