package com.project.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.constant.Gender;
import com.project.dto.UserFilterDTO;
import com.project.entity.User;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import com.project.specification.UserSpecification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {

//	private final UserMapper userMapper;

	private final UserRepository userRepository;
//	private final SkillRepository skillRepository;
//	private final UserSkillRepository userSkillRepository;

	/**
	 * 1. Find gender-wise user count (e.g., Male, Female, Other).
	 */

	public Map<Gender, Long> genderWiseUserCount() {

		List<User> users = userRepository.findAll();

		Map<Gender, Long> genderWiseCount = users.stream()
				.collect(Collectors.groupingBy(u -> u.getGender(), Collectors.counting()));
		return genderWiseCount;

	}

	/**
	 * 2. Find age-wise user bifurcation (e.g., <18, 18-25, 26-35, 36-50, >50).
	 */
	
	public Map<String, List<String>>  ageWiseBifurcation(){
		List<User> users = userRepository.findAll();
		
		return users.stream().collect(Collectors.groupingBy(
				user ->  {
					Period period = Period.between(user.getDob(), LocalDate.now());
					int age = period.getYears();
					
					if(age<18) {
						return "<18";
					}
					else if(age>=18 && age<=25) {
						return "18-25";
					}else if (age >= 21 && age <= 25) {
						return "21-25";
					}else if(age>=26 && age<=35) {
						return "26-35";
					}else if(age>=36 && age<=50) {
						return "36-50";
					}else{
						return ">50";
					}
				},
				Collectors.collectingAndThen(
						Collectors.toList(), 
						list->list.stream()
						          .map(u -> u.getFullName())
						          .toList())
				
		));

	}

	/**
	 * Apply filters & pagination
	 */
	@Override
	public Page<User> searchAllUsers(UserFilterDTO userFilter, Integer pageNumber, Integer pageSize) {
		log.info("Search users with filters & pagination");

		Specification<User> specification = new UserSpecification(userFilter);
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return userRepository.findAll(specification, pageable);
	}

}
