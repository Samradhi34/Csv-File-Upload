package com.project.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidation implements ConstraintValidator<ValidGender, String>  {
	

	 @Override
	    public boolean isValid(String gender, ConstraintValidatorContext context) {
	        if (gender == null || gender.trim().isEmpty()) {
	            return false; 
	        }
	        /** Case-insensitive check for allowed genders
	         * 
	         */
	        return gender.equalsIgnoreCase("MALE") || 
	               gender.equalsIgnoreCase("FEMALE") || 
	               gender.equalsIgnoreCase("OTHER");
	    }
}
