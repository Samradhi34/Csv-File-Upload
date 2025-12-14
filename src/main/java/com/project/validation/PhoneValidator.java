package com.project.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String>{

	@Override
	public boolean isValid(String phoneField, ConstraintValidatorContext context) {
		if (phoneField == null) {
			return false;
		} else if (phoneField.length() != 10) {
			return false;
		}
		return phoneField.matches("[0-9()-]*");
	}

}
