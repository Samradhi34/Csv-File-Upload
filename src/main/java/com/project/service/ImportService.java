package com.project.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.project.exception.ValidationException;

public interface ImportService {
	
	/**
	 * Uploading CSV file
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ValidationException 
	 */
	String importCSV(MultipartFile file) throws IOException, ValidationException;

}
