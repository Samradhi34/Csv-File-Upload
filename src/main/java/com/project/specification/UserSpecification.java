package com.project.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.project.dto.UserFilterDTO;
import com.project.entity.User;
import com.project.entity.UserSkill;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserSpecification implements Specification<User>{
	
	private static final long serialVersionUID = 7743526983242630213L;
	
	private final UserFilterDTO userFilter;

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		
		log.info("*****User Specification with filter***** {}", userFilter);
		
		List<Predicate> predicates = new ArrayList<>();
		
		
		/**
		 * Search by name or email
		 */
		if (userFilter.getSearch() != null && !userFilter.getSearch().isEmpty()) {
            String likePattern = "%" + userFilter.getSearch().toLowerCase() + "%";
            predicates.add(cb.or(
                    cb.like(cb.lower(root.get("fullName")), likePattern),
                    cb.like(cb.lower(root.get("email")), likePattern)
            ));
        }
		
		/**
		 * Filter by gender
		 */
		if(userFilter.getGender() != null) {
			predicates.add(cb.equal(root.get("gender"), userFilter.getGender()));
		}
		
		/**
		 * Filter by marital status
		 */
		if (userFilter.getMaritalStatus() != null) {
            predicates.add(cb.equal(root.get("maritalStatus"), userFilter.getMaritalStatus()));
        }
		
		/**
		 * Filter by skill name
		 */
		if (userFilter.getSkillName() != null && !userFilter.getSkillName().isEmpty()) {
            Join<User, UserSkill> skillJoin = root.join("userSkills");
            predicates.add(cb.equal(cb.lower(skillJoin.get("skillName")), userFilter.getSkillName().toLowerCase()));
        }
		
		/**
		 * Filter by date of birth range
		 */
		if (userFilter.getDobFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("dob"), userFilter.getDobFrom()));
        }
        if (userFilter.getDobTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("dob"), userFilter.getDobTo()));
        }
		
        /**
         * To avoid duplicates due to join
         */
        query.distinct(true);
        
		return cb.and(predicates.toArray(new Predicate[0]));
	}

}
