package com.ezicrm.eziCRM.model.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<ValidStatus, Integer> {

    @Override
    public void initialize(ValidStatus constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer status, ConstraintValidatorContext context) {
        return status != null && (status == 0 || status == 1);
    }
}
