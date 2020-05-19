package com.kokotripadmin.validator;

import javax.validation.ConstraintValidatorContext;

public class BaseValidator {
    protected void setErrorMessage(ConstraintValidatorContext constraintValidatorContext, String message) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
