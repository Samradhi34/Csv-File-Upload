package com.project.service;

import org.springframework.data.domain.Page;

import com.project.dto.UserFilterDTO;
import com.project.entity.User;

public interface UserService {
	

	/**
	 * Apply Specification & Pagination
	 * 
	 * @param userFilter
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Page<User> searchAllUsers(UserFilterDTO userFilter, Integer pageNumber, Integer pageSize);
	
}
