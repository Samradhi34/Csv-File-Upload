package com.project.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.exception.ValidationException;
import com.project.locale.MessageByLocaleService;
import com.project.response.GenericResponseHandlers;
import com.project.service.ImportService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/import")
@RequiredArgsConstructor
public class ImportController {

	private final MessageByLocaleService messageByLocaleService;
	private final ImportService importService;

	/**
	 * Import CSV
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ValidationException 
	 */

	@Operation(summary = "Upload the csv file")
	@PostMapping
	public ResponseEntity<?> importCSV(@RequestParam("file") MultipartFile file) throws IOException, ValidationException {

		log.info("Inside 'importCSV' method in user controller");
		String importCSV = importService.importCSV(file);
		log.info("Outside 'importCSV' method in user controller");

		return new GenericResponseHandlers.Builder().setData(importCSV).setStatus(HttpStatus.CREATED)
				.setMessage(messageByLocaleService.getMessage("add.success", new Object[] { " User " })).create();

	}

}
