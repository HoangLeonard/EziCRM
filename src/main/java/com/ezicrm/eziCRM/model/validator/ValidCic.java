package com.ezicrm.eziCRM.model.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CicValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCic {
    String message() default "Invalid cic value.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
