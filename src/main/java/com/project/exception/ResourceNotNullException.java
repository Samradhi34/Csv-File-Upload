package com.project.exception;

import lombok.Getter;

@Getter
public class ResourceNotNullException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8832584472016643605L;
	
	
	private String message;
	public ResourceNotNullException(String message) {
		super();
		this.message=message;
		
	}
	
    
}