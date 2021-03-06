package com.kokotripadmin.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ImageValidator.class)
@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageConstraint {
    String message() default "Invalid image";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
