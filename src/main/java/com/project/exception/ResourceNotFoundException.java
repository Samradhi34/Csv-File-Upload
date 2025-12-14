package com.project.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8832584472016643605L;
	
	
	private String message;
	public ResourceNotFoundException(String message) {
		super();
		this.message=message;
		
	}
	
    
}