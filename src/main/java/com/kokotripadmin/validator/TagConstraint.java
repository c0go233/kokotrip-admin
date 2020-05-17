package com.kokotripadmin.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = TagValidator.class)
@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TagConstraint {

    String message() default "Invalid tag";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
