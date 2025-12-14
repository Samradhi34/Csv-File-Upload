package com.project.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "skill")
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Skill extends CommonModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7500028159132757790L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "skill", fetch = FetchType.LAZY)
    private List<UserSkill> userSkills;

}
