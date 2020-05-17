package com.kokotripadmin.validator;

import com.kokotripadmin.constant.DayOfWeekEnum;
import com.kokotripadmin.constant.TradingHourTypeEnum;
import com.kokotripadmin.viewmodel.common.TradingHourVm;
import com.kokotripadmin.viewmodel.tourspot.TourSpotVm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Time;
import java.util.List;


//opentime > closetime
//same time like 5:00 - 5:00
//there are more than one row for the dayoff
//two or more off on the same day
//schedule overlap on the same day
public class TradingHourValidator implements ConstraintValidator<TradingHourConstraint, TourSpotVm> {


    @Override
    public void initialize(TradingHourConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(TourSpotVm tourSpotVm, ConstraintValidatorContext constraintValidatorContext) {

        if (tourSpotVm.isAlwaysOpen()) return true;

        List<TradingHourVm> tradingHourVmList = tourSpotVm.getTradingHourVmList();

        if (tradingHourVmList == null || tradingHourVmList.isEmpty()) {
            setErrorMessage(constraintValidatorContext, "영업시간을 추가해주세요.");
            return false;
        }

        TradingHourVm previous = tradingHourVmList.get(0);
//        if (!isOpenTimeEarlierThanCloseTime(previous, constraintValidatorContext)) return false;

        for (int i = 1; i < tradingHourVmList.size(); i++) {
            TradingHourVm current = tradingHourVmList.get(i);
//            if (!isOpenTimeEarlierThanCloseTime(current, constraintValidatorContext)) return false;

            if (previous.getDayOfWeekId() == current.getDayOfWeekId()) {
                if (closedDayHaveTradingHour(previous, current, constraintValidatorContext)) return false;
                if (closedTimeConflictOpenTime(previous, current, constraintValidatorContext)) return false;
            }
            previous = current;
        }

        return true;
    }

    private boolean closedTimeConflictOpenTime(TradingHourVm previous, TradingHourVm current,
                                               ConstraintValidatorContext constraintValidatorContext) {

        if (previous.getDayOfWeekId() == current.getDayOfWeekId()) {
            if (current.getOpenTime().compareTo(previous.getCloseTime()) < 0) {
                setErrorMessage(constraintValidatorContext, closedTimeConflictsOpenTimeErrorMessage(previous, current));
                return true;
            }
        }
        return false;
    }

    private String closedTimeConflictsOpenTimeErrorMessage(TradingHourVm previous, TradingHourVm current) {

        String dayOfWeek = DayOfWeekEnum.valueOf(previous.getDayOfWeekId()).get().name();
        String message = dayOfWeek + " 마감시간(" + trimTime(previous.getCloseTime()) +
                         ") 이 다음 오픈시간(" + trimTime(current.getOpenTime()) + ") 과 겹칩니다.";
        return message;
    }

    private boolean isOpenTimeEarlierThanCloseTime(TradingHourVm tradingHourVm,
                                                   ConstraintValidatorContext constraintValidatorContext) {

        boolean closed = tradingHourVm.getTradingHourTypeId() == TradingHourTypeEnum.휴무.getId();
        int difference = closed ? 1 : tradingHourVm.getCloseTime().compareTo(tradingHourVm.getOpenTime());
        if (!closed && difference <= 0) {
            setErrorMessage(constraintValidatorContext, invalidTradingHourErrorMessage(tradingHourVm));
            return false;
        }
        return true;
    }

    private String invalidTradingHourErrorMessage(TradingHourVm tradingHourVm) {

        String dayOfWeek = DayOfWeekEnum.valueOf(tradingHourVm.getDayOfWeekId()).get().name();
        String message = dayOfWeek + " 오픈시간(" + trimTime(tradingHourVm.getOpenTime()) +
                         ") 이 마감시간(" + trimTime(tradingHourVm.getCloseTime()) + ") 보다 같거나 클수없습니다.";
        return message;
    }

    private String trimTime(Time time) {
        String timeInString = time.toString();
        int lastIndexOfColon = timeInString.lastIndexOf(':');
        return timeInString.substring(0, lastIndexOfColon);
    }

    private boolean closedDayHaveTradingHour(TradingHourVm previous, TradingHourVm current,
                                             ConstraintValidatorContext constraintValidatorContext) {

        boolean isPreviousClosed = previous.getTradingHourTypeId() == TradingHourTypeEnum.휴무.getId();
        boolean isCurrentClosed = current.getTradingHourTypeId() == TradingHourTypeEnum.휴무.getId();

        if (isPreviousClosed || isCurrentClosed) {
            setErrorMessage(constraintValidatorContext, duplicateTradingHourErrorMessage(previous));
            return true;
        }
        return false;


//        if (previous.getDayOfWeekId() == current.getDayOfWeekId()
//                && (previous.getTradingHourTypeId() == TradingHourTypeEnum.휴무.getId()
//                        || current.getTradingHourTypeId() == TradingHourTypeEnum.휴무.getId())) {
//            setErrorMessage(constraintValidatorContext, duplicateTradingHourErrorMessage(previous));
//            return true;
//        }
//        return false;
    }

    private String duplicateTradingHourErrorMessage(TradingHourVm tradingHourVm) {

        String dayOfWeek = DayOfWeekEnum.valueOf(tradingHourVm.getDayOfWeekId()).get().name();
        String message = dayOfWeek + " 휴무일에는 다른 영업시간이나 휴무일을 중복 추가할수없습니다.";
        return message;
    }

    private void setErrorMessage(ConstraintValidatorContext constraintValidatorContext, String message) {

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

}

