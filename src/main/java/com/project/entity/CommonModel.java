package com.project.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

/**
 * Author: Kody technolab Pvt.Ltd.
 * Date: 23-Sept-2025
 */
@Data
@MappedSuperclass
public class CommonModel implements Serializable{

	/**
	 * Here , we can write our common fields
	 * 
	 */
	private static final long serialVersionUID = -8119744246605620519L;

	
	@Column(name = "active" ,nullable = false)
	private Boolean active;
	
	@CreationTimestamp
	@Column(name="created_at" ,nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at" ,nullable = false)
	private LocalDateTime updatedAt;
	

}
