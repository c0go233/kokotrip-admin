package com.kokotripadmin.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TradingHourValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TradingHourConstraint {
    String message() default "Invalid trading hour";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
