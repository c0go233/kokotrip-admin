package com.kokotripadmin.validator;

import javax.validation.ConstraintValidatorContext;

public abstract class Validator {


    protected void setErrorMessage(ConstraintValidatorContext constraintValidatorContext, String message) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
