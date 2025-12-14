package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.UserSkill;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long>{

}
