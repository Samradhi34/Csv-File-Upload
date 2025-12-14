package com.project.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.project.constant.Gender;
import com.project.constant.MaritalStatus;
import com.project.validators.ValidEnum;

import lombok.Data;

@Data
public class UserRequestDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1716480238949658677L;
	
	private String fullName;
	
	private String email;
	
	private LocalDate dob;
	
	@ValidEnum(enumClass = Gender.class, message = "Invalid gender value")
	private String gender;
	
	 @ValidEnum(enumClass = MaritalStatus.class, message = "Invalid marital status")
	private String maritalStatus;

}
