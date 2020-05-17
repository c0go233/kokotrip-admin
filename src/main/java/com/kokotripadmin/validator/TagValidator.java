package com.kokotripadmin.validator;

import com.kokotripadmin.viewmodel.tag.TagVm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;

public class TagValidator implements ConstraintValidator<TagConstraint, List<TagVm>> {

    @Override
    public void initialize(TagConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<TagVm> tagViewModelList, ConstraintValidatorContext constraintValidatorContext) {
        if (tagViewModelList == null || tagViewModelList.size() == 0) return true;
        HashSet<String> tagNames = new HashSet<>();
        for (TagVm tagViewModel : tagViewModelList) {
            if (tagNames.contains(tagViewModel.getName())) {
                setErrorMessage(constraintValidatorContext, tagViewModel.getName() + " 이(가) 여러개 있다 지워조라!!");
                return false;
            }
            tagNames.add(tagViewModel.getName());
        }
        return true;
    }

    private void setErrorMessage(ConstraintValidatorContext constraintValidatorContext, String message) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
