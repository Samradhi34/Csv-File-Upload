package com.project.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.constant.Gender;
import com.project.constant.MaritalStatus;
import com.project.entity.Skill;
import com.project.entity.User;
import com.project.entity.UserSkill;
import com.project.exception.ValidationException;
import com.project.repository.SkillRepository;
import com.project.repository.UserRepository;
import com.project.repository.UserSkillRepository;
import com.project.service.ImportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Throwable.class)
public class ImportServiceImpl implements ImportService{

	private final UserRepository userRepository;
	private final SkillRepository skillRepository;
	private final UserSkillRepository userSkillRepository;

	@Override
	public String importCSV(MultipartFile file) throws IOException, ValidationException {

		log.info("Inside 'importCSV' method in UserServiceImpl");

		if (file == null || file.isEmpty()) {
			throw new ValidationException("CSV file cannot be empty!");
		}

		try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

			String line;
			boolean header = true;

			/**
			 * Converting Date into LocalDate using DateTimeFormatter
			 */
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

			while ((line = br.readLine()) != null) {

				/**
				 * Skip first line as it consists header name
				 */

				if (header) {
					header = false;
					continue; // skip header
				}

				String[] data = line.split(",");

				if (data.length < 6) {
					System.out.println("Skipping invalid row: " + line);
					continue;
				}

				/**
				 * Extract values
				 */

				String fullName = data[0].trim();
				String email = data[1].trim();
				String csvGender = data[2].trim();
				String csvMaritalStatus = data[3].trim();
				String skillsData = data[4].trim();
				LocalDate dob = LocalDate.parse(data[5].trim(), formatter);

				/**
				 * Converting string into Gender enum
				 */
				Gender gender;
				try {
					gender = Gender.valueOf(csvGender.trim().toUpperCase());
				} catch (IllegalArgumentException e) {
					throw new RuntimeException("Invalid gender: " + csvGender);
				}

				/**
				 * MaritalStatus Enum mapping - CSV string to enum
				 */
				String csvStatus = csvMaritalStatus.trim().replaceAll("\\s+", " "); // remove extra spaces

				MaritalStatus maritalStatus;
				switch (csvStatus.toLowerCase()) {
				case "single":
					maritalStatus = MaritalStatus.SINGLE;
					break;
				case "married":
					maritalStatus = MaritalStatus.MARRIED;
					break;
				case "divorced":
					maritalStatus = MaritalStatus.DIVORCED;
					break;
				case "prefer not to answer":
					maritalStatus = MaritalStatus.PREFER_NOT_TO_ANSWER;
					break;
				default:
					throw new IllegalArgumentException("Invalid marital status: " + csvStatus);
				}

				User user = User.builder().fullName(fullName).email(email).dob(dob).gender(gender)
						.maritalStatus(maritalStatus).build();

				user.setActive(true);

				/**
				 *  Save user in DB
				 */
				User savedUser = userRepository.save(user);

				/**
				 *  Save user skills
				 */
				if (!skillsData.isEmpty()) {

					String[] skillPairs = skillsData.split(";");

					for (String skillPair : skillPairs) {

						if (skillPair.trim().isEmpty())
							continue;

						String[] parts = skillPair.trim().split("-");
						String skillName = parts[0].trim().toLowerCase().replaceAll("\\s+", " ");
						int rating = 0;
						if (parts.length > 1) {
							try {
								rating = Integer.parseInt(parts[1].trim());
							} catch (NumberFormatException e) {
								rating = 0;
							}
						}

						/**
						 *  Check if skill exists
						 */
						Skill skill = skillRepository.findByNameIgnoreCase(skillName).orElseGet(() -> {
							Skill newSkill = new Skill();
							newSkill.setName(skillName);
							newSkill.setActive(true);
							return skillRepository.save(newSkill);
						});

						/**
						 *  Save mapping
						 */
						UserSkill userSkill = UserSkill.builder().user(savedUser).skill(skill).rating(rating).build();

						userSkill.setActive(true);

						userSkillRepository.save(userSkill);
					}
				}
			}
		}

		return "CSV imported successfully!";
	}

}
