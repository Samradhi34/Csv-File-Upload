 package com.project.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Null or blank can be treated as invalid, change if optional
        if (value == null || value.isBlank()) {
            return false;
        }

        // Normalize CSV input: trim, uppercase, replace spaces with underscores
        String normalizedValue = value.trim().toUpperCase();

        // Loop through enum constants
        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.name().equals(normalizedValue)) {
                return true;
            }
        }

        return false;
    }
}
