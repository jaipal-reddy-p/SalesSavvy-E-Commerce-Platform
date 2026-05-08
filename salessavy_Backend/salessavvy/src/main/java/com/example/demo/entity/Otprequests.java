package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;

@Entity
@Table(name="otprequests")
public class Otprequests {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false,  name = "email", unique = true)
	private String email;

	@Column(nullable = false,  name = "otp",length = 6)
	private String otp;

	@Column(nullable = false,  name = "created_at")
	private LocalDateTime created_at;
	
	public Otprequests() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Otprequests(Long id, String email, String otp, LocalDateTime created_at) {
		super();
		this.id = id;
		this.email = email;
		this.otp = otp;
		this.created_at = created_at;
	}

	

	public Otprequests(String email, String otp, LocalDateTime created_at) {
		super();
		this.email = email;
		this.otp = otp;
		this.created_at = created_at;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
}
