<%@ tag description="Main Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ attribute required="true" name="dayOfWeekLinkedHashMap" type="java.util.LinkedHashMap" %>
<%@ attribute required="true" name="tradingHourTypeLinkedHashMap" type="java.util.LinkedHashMap" %>
<%@ attribute required="true" name="tradingHourList"
              type="java.util.List<com.kokotripadmin.viewmodel.common.TradingHourVm>" %>


<div id="timetable-builder-container">
    <div class="timetable-builder-wrapper">
        <div class="timetable-builder__item">
            <span class="timetable-builder__heading">요일</span>
            <div class="timetable-builder__input-wrapper">
                <t:dropdown selectedKey="${dayOfWeekLinkedHashMap.entrySet().iterator().next().key}"
                            optionLinkedHashMap="${dayOfWeekLinkedHashMap}"
                            dropdownInputId="timetable-day-of-week-input"
                            extraClassForDropdown="timetable-builder__medium-dropdown"
                            extraClassForOptionWrapper="dropdown__day-of-week-option-wrapper"/>
            </div>
        </div>

        <div class="timetable-builder__item">
            <span class="timetable-builder__heading">영업유무</span>
            <div class="timetable-builder__input-wrapper">
                <t:dropdown selectedKey="${tradingHourTypeLinkedHashMap.entrySet().iterator().next().key}"
                            optionLinkedHashMap="${tradingHourTypeLinkedHashMap}"
                            dropdownInputId="timetable-trading-hour-type-input"
                            extraClassForDropdown="timetable-builder__small-dropdown"
                            extraClassForOptionWrapper="timetable-builder__trading-hour-type-option-wrapper"/>
            </div>

        </div>

        <div class="timetable-builder__item">
            <span class="timetable-builder__heading">오픈</span>
            <div class="timetable-builder__multi-dropdown-wrapper timetable-builder__input-wrapper">

                <t:timeDropdown defaultTime="12" maximumTime="23" dropdownInputId="timetable-builder-open-hour-input"
                                extraClassForDropdown="timetable-builder__small-dropdown timetable-builder__time-dropdown"
                                extraClassForOptionWrapper="dropdown__time-option-wrapper dropdown__open-time-option-wrapper"/>
                <span>:</span>

                <t:timeDropdown defaultTime="59" maximumTime="59" dropdownInputId="timetable-builder-open-minute-input"
                                extraClassForDropdown="timetable-builder__small-dropdown timetable-builder__time-dropdown"
                                extraClassForOptionWrapper="dropdown__time-option-wrapper dropdown__open-time-option-wrapper"/>
            </div>
        </div>

        <div class="timetable-builder__item">
            <span class="timetable-builder__heading">마감</span>
            <div class="timetable-builder__multi-dropdown-wrapper timetable-builder__input-wrapper">

                <t:timeDropdown defaultTime="23" maximumTime="23" dropdownInputId="timetable-builder-close-hour-input"
                                extraClassForDropdown="timetable-builder__small-dropdown timetable-builder__time-dropdown"
                                extraClassForOptionWrapper="dropdown__time-option-wrapper"/>
                <span>:</span>

                <t:timeDropdown defaultTime="59" maximumTime="59" dropdownInputId="timetable-builder-close-minute-input"
                                extraClassForDropdown="timetable-builder__small-dropdown timetable-builder__time-dropdown"
                                extraClassForOptionWrapper="dropdown__time-option-wrapper"/>

            </div>
        </div>

        <div class="timetable-builder__item">
            <span class="timetable-builder__heading timetable-builder__empty-label"></span>
            <div>
                <button id="timetable-add-btn" type="button" class="btn-bold btn timetable-builder__btn">추가하기</button>
            </div>
        </div>
    </div>







    <div id="timetable-trading-hour-wrapper">
        <c:forEach var="tradingHour" items="${tradingHourList}">



            <div role="row" class="timetable-builder-wrapper" data-day-of-week-id="${tradingHour.dayOfWeekId}"
                 data-open-time="${tradingHour.openTime.toString()}">


                <div class="timetable-builder__item">
                    <div class="timetable-builder__input-wrapper">

                        <t:dropdown selectedKey="${tradingHour.dayOfWeekId}"
                                    optionLinkedHashMap="${dayOfWeekLinkedHashMap}"
                                    dropdownInputId=""
                                    dropdownInputName="tradingHourVmList[0].dayOfWeekId"
                                    extraClassForDropdown="timetable-builder__medium-dropdown"
                                    extraClassForOptionWrapper="dropdown__day-of-week-option-wrapper"/>
                    </div>
                </div>

                <div class="timetable-builder__item">
                    <div class="timetable-builder__input-wrapper">


                        <t:dropdown selectedKey="${tradingHour.tradingHourTypeId}"
                                    optionLinkedHashMap="${tradingHourTypeLinkedHashMap}"
                                    dropdownInputId=""
                                    dropdownInputName="tradingHourVmList[0].tradingHourTypeId"
                                    extraClassForDropdown="timetable-builder__small-dropdown"
                                    extraClassForOptionWrapper="timetable-builder__trading-hour-type-option-wrapper"/>
                    </div>

                </div>

                <div class="timetable-builder__item">
                    <div class="timetable-builder__multi-dropdown-wrapper timetable-builder__input-wrapper">

                        <input type="hidden" name="tradingHourVmList[0].openTime" value="${tradingHour.openTime}">

                        <t:timeDropdown defaultTime="${tradingHour.openTime.hours}" maximumTime="23"
                                        dropdownInputId=""
                                        extraClassForDropdown="timetable-builder__small-dropdown timetable-builder__time-dropdown"
                                        extraClassForOptionWrapper="dropdown__time-option-wrapper dropdown__open-time-option-wrapper"/>
                        <span>:</span>
                        <t:timeDropdown defaultTime="${tradingHour.openTime.minutes}" maximumTime="59"
                                        dropdownInputId=""
                                        extraClassForDropdown="timetable-builder__small-dropdown timetable-builder__time-dropdown"
                                        extraClassForOptionWrapper="dropdown__time-option-wrapper dropdown__open-time-option-wrapper"/>
                    </div>
                </div>

                <div class="timetable-builder__item">
                    <div class="timetable-builder__multi-dropdown-wrapper timetable-builder__input-wrapper">

                        <input type="hidden" name="tradingHourVmList[0].closeTime" value="${tradingHour.closeTime}">

                        <t:timeDropdown defaultTime="${tradingHour.closeTime.hours}" maximumTime="23"
                                        dropdownInputId=""
                                        extraClassForDropdown="timetable-builder__small-dropdown timetable-builder__time-dropdown"
                                        extraClassForOptionWrapper="dropdown__time-option-wrapper"/>
                        <span>:</span>
                        <t:timeDropdown defaultTime="${tradingHour.closeTime.minutes}" maximumTime="59"
                                        dropdownInputId=""
                                        extraClassForDropdown="timetable-builder__small-dropdown timetable-builder__time-dropdown"
                                        extraClassForOptionWrapper="dropdown__time-option-wrapper"/>

                    </div>
                </div>

                <div class="timetable-builder__item">
                    <button type="button"
                            class="timetable-builder__btn timetable-builder__delete-btn btn-close"></button>
                </div>
            </div>

        </c:forEach>
    </div>
</div>




