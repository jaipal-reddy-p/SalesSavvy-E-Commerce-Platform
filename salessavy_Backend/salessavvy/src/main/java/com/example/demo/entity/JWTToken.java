package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="jwt_tokens")
public class JWTToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//Sprecifies that the tokenid will be auto-generated.
	private int tokenId; //Stores the unique identifier for each token.
	
	@ManyToOne // Establishes a many-to-one relationship with the User entity.
	@JoinColumn(name = "user_id",nullable = false)// Links the token to a specific user in the users table.
	private User user; // Represents the user associated with the token.
	
	@Column(nullable = false) // Ensures that the token cannot be null.
	private String token; // Stores the JWT token string.
	
	@Column(nullable = false) // Ensures that the expiration time cannot be null.
	private LocalDateTime expiresAt; // Stores the expiration time of the token.
	
	//default constructor
	public JWTToken() {
		// TODO Auto-generated constructor stub
	}

	public JWTToken(User user, String token, LocalDateTime expiresAt) {
		super();
		this.user = user;
		this.token = token;
		this.expiresAt = expiresAt;
	}

	public JWTToken(int tokenId, User user, String token, LocalDateTime expiresAt) {
		super();
		this.tokenId = tokenId;
		this.user = user;
		this.token = token;
		this.expiresAt = expiresAt;
	}
	
	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
}
