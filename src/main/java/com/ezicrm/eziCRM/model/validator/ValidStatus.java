package com.ezicrm.eziCRM.model.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StatusValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStatus {
    String message() default "Invalid status value.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
