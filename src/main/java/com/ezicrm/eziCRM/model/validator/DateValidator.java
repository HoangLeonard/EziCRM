
package com.ezicrm.eziCRM.model.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.security.InvalidParameterException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.Month;


public class DateValidator implements ConstraintValidator<ValidDate, Date> {
    public static final int MIN_AGE = 18;
    public static final int MAX_AGE = 100;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Date birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null)
            return true;

        LocalDate birthLocalDate = birthDate.toLocalDate();
        LocalDate today = LocalDate.now();
        int age = Period.between(birthLocalDate, today).getYears();

        return age >= MIN_AGE && age <= MAX_AGE;
    }



    public static boolean isValidDate(String dateStr) {
        // Kiểm tra định dạng ngày tháng năm
        if (!dateStr.matches("\\d{4}/\\d{2}/\\d{2}")) {
            return false;
        }

        // Tách ngày, tháng và năm
        String[] parts = dateStr.split("/");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        if (month < 1 || month > 12 ) return false;
        else {
            // Kiểm tra số ngày tối đa của tháng
            int maxDayOfMonth = Month.of(month).length(LocalDate.of(year, month, 1).isLeapYear());

            // Kiểm tra xem ngày tháng năm có hợp lệ không
            return day <= maxDayOfMonth;
        }
    }

    public static void main(String[] args) {
        String dateOfBirth = "21/13/2023";
        if (isValidDate(dateOfBirth)) {
            System.out.println("Ngày tháng năm sinh hợp lệ.");
        } else {
            System.out.println("Ngày tháng năm sinh không hợp lệ.");
        }
    }
}
