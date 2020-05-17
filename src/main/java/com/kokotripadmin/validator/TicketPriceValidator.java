package com.kokotripadmin.validator;



import com.kokotripadmin.viewmodel.ticket.TicketPriceVm;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;

public class TicketPriceValidator extends Validator implements ConstraintValidator<TicketPriceConstraint, List<TicketPriceVm>> {

    @Override
    public void initialize(TicketPriceConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<TicketPriceVm> ticketPriceViewModelList,
                           ConstraintValidatorContext constraintValidatorContext) {

        if (ticketPriceViewModelList == null || ticketPriceViewModelList.size() == 0) {
            setErrorMessage(constraintValidatorContext, "가격을 추가해주세요.");
            return false;
        }
        return checkDuplicateAndRepPrice(ticketPriceViewModelList, constraintValidatorContext);
    }

    private boolean checkDuplicateAndRepPrice(List<TicketPriceVm> ticketPriceViewModelList,
                                                ConstraintValidatorContext constraintValidatorContext) {
        HashSet<Integer> ticketTypes = new HashSet<>();
        int repPriceCount = 0;

        for (TicketPriceVm viewModel : ticketPriceViewModelList) {
            int ticketTypeId = viewModel.getTicketTypeId();
            if (ticketTypes.contains(ticketTypeId)) {
                setErrorMessage(constraintValidatorContext, "중복 티켓타입이 존재합니다.");
                return false;
            }
            ticketTypes.add(ticketTypeId);

            if (viewModel.isRepPrice()) repPriceCount++;
        }

        if (repPriceCount == 0) {
            setErrorMessage(constraintValidatorContext, "대표가격을 선택해주세요");
            return false;
        }

        if (repPriceCount > 1) {
            setErrorMessage(constraintValidatorContext, "대표가격은 한개만 선택 가능합니다");
            return false;
        }

        return true;
    }
}
