package com.project.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.UserFilterDTO;
import com.project.entity.User;
import com.project.locale.MessageByLocaleService;
import com.project.mapper.UserMapper;
import com.project.response.GenericResponseHandlers;
import com.project.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final MessageByLocaleService messageByLocaleService;
	private final UserService userService;
	private final UserMapper mapper;
	
	/**
	 * Filter & Pagination 
	 * 
	 * @param userFilter
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@Operation(summary = "Get all Users with filters & pagination")
	@GetMapping
	public ResponseEntity<?> getAllUsers(
			@ModelAttribute UserFilterDTO userFilter,
			@RequestParam(required = false, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "5") Integer pageSize) {

		log.info("Inside 'getAllUsers' method in UserController");
		
	    String search = null;
	    if (userFilter.getSearch() != null && !userFilter.getSearch().isBlank()) {
	        search = userFilter.getSearch().trim();
	    } else if (userFilter.getEmail() != null && !userFilter.getEmail().isBlank()) {
	        search = userFilter.getEmail().trim();
	    } else if (userFilter.getName() != null && !userFilter.getName().isBlank()) {
	        search = userFilter.getName().trim();
	    }
	    userFilter.setSearch(search);

		Page<User> allUsers = userService.searchAllUsers(userFilter, pageNumber - 1, pageSize);

		log.info("Outside 'getAllUsers' method in UserController");
		return new GenericResponseHandlers.Builder()
				.setData(mapper.toDtos(allUsers.getContent()))
				.setPageSize(pageSize)
				.setPageNumber(pageNumber).setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("page.fetched.success", new Object[] { " User " }))
				.setHasNextPage(allUsers.hasNext()).setHasPreviousPage(allUsers.hasPrevious())
				.setTotalPages(allUsers.getTotalPages()).create();

	}
	
	

}
