package com.ezicrm.eziCRM.model.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.equals("Male") || s.equals("Female");
    }

    @Override
    public void initialize(ValidGender constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
