package com.project.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isEmpty()) {
			return false;
		}
		boolean startWithCapital = Character.isUpperCase(value.charAt(0));
		boolean hasNoDigits = value.chars().noneMatch(Character::isDigit);
		return startWithCapital && hasNoDigits;
	}

}
