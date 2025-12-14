package com.project.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.project.constant.Gender;
import com.project.constant.MaritalStatus;

import lombok.Data;

@Data
public class UserFilterDTO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -314310126011280505L;

	private String name;
	private String email;
	private String search;
	private Gender gender;
	private MaritalStatus maritalStatus;
	private String skillName;
	private LocalDate dobFrom;
	private LocalDate dobTo;

}
