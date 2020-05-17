package com.kokotripadmin.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TicketPriceValidator.class)
@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TicketPriceConstraint {

    String message() default "Invalid ticket price";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
