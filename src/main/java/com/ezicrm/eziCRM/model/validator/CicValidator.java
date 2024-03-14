package com.ezicrm.eziCRM.model.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CicValidator implements ConstraintValidator<ValidCic, String> {
    @Override
    public void initialize(ValidCic constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() == 12 && s.matches("\\d+");
    }

//    public static void main(String[] args) {
//        String s = "231412341a2313";
//
//        System.out.println(s.length() == 12 && s.matches("\\d+"));
//    }
}
