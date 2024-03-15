package com.ezicrm.eziCRM.model.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GenderValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGender {
    String message() default "Invalid gender value.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
