package com.counsellorportal.project.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "counsellor_tbl")
public class Counsellor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer counsellorId;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	
	private String pwd;
	
	private String phNo;
	
	@CreationTimestamp
	private LocalDate created_date;
	
	@UpdateTimestamp
	private LocalDate  updated_date;

}
