package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.demo.entity.JWTToken;
import com.example.demo.entity.Otprequests;
import com.example.demo.entity.User;
import com.example.demo.repository.JWTTokenRepository;
import com.example.demo.repository.OtpRepository;
import com.example.demo.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.nio.charset.StandardCharsets;

@Service
public class AuthService {
	private final Key SIGNING_KEY;
	private final UserRepository userRepository;
	private final JWTTokenRepository jwtTokenRepository;
	private final OtpRepository otpRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JavaMailSender mailSender;
	private String mail=null;

	@Autowired
	public AuthService(
			UserRepository userRepository,
			JWTTokenRepository jwtTokenRepository,
			OtpRepository otpRepository,
			JavaMailSender mailSender,
			@Value("${jwt.secret}") String jwtSecret) {
		this.userRepository = userRepository;
		this.jwtTokenRepository = jwtTokenRepository;
		this.otpRepository = otpRepository;
		this.mailSender = mailSender;
		this.passwordEncoder = new BCryptPasswordEncoder();

		if (jwtSecret.getBytes(StandardCharsets.UTF_8).length < 64) {
			throw new IllegalArgumentException("JWT_SECRET in application.properties must be at least 64 bytes long for HS512.");
		}
		this.SIGNING_KEY = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}

	public User authenticate(String username, String password) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Invalid username or password"));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Invalid username or password");
		}

		return user;
	}

	public String generateToken(User user) {
		String token;
		LocalDateTime now = LocalDateTime.now();
		JWTToken existingToken = jwtTokenRepository.findByUserId(user.getUserId());

		if (existingToken != null && now.isBefore(existingToken.getExpiresAt())) {
			token = existingToken.getToken();
		} else {
			token = generateNewToken(user);
			if (existingToken != null) {
				jwtTokenRepository.delete(existingToken);
			}
			saveToken(user, token);
		}

		return token;
	}

	private String generateNewToken(User user) {
		return Jwts.builder()
				.setSubject(user.getUsername())
				.claim("role", user.getRole().name())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
				.signWith(SIGNING_KEY, SignatureAlgorithm.HS512)
				.compact();
	}

	public void saveToken(User user, String token) {
		JWTToken jwtToken = new JWTToken(user, token, LocalDateTime.now().plusHours(1));
		jwtTokenRepository.save(jwtToken);
	}

	public void logout(User user) {
		jwtTokenRepository.deleteByUserId(user.getUserId());
	}

	public boolean validateToken(String token) {
		try {
			System.err.println("VALIDATING TOKEN...");
			Jwts.parserBuilder()
			.setSigningKey(SIGNING_KEY)
			.build()
			.parseClaimsJws(token);

			Optional<JWTToken> jwtToken = jwtTokenRepository.findByToken(token);

			if (jwtToken.isPresent()) {
				return jwtToken.get().getExpiresAt().isAfter(LocalDateTime.now());
			}

			return false;
		} catch (Exception e) {
			System.err.println("Token validation failed: " + e.getMessage());
			return false;
		}
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(SIGNING_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	public void processForgotPassword(String email) {
		this.mail = email;

		Optional<User> userOptional = userRepository.findByEmail(email);
		if (userOptional.isEmpty()) {
			throw new IllegalArgumentException("Email not found in the system");
		}

		String otp = generateOtp();
		Otprequests otprequests = new Otprequests();
		otprequests.setEmail(email);
		otprequests.setOtp(otp);
		otprequests.setCreated_at(LocalDateTime.now());
		otpRepository.save(otprequests);

		sendEmail(email, "Your OTP for Password Reset", "Your OTP is: " + otp);
	}

	private String generateOtp() {
		Random random = new Random();
		return String.valueOf(100000 + random.nextInt(900000)); // Generate a 6-digit OTP
	}

	private void sendEmail(String to, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			mailSender.send(message);
			System.out.println("Email sent successfully to " + to);
		} catch (Exception e) {
			throw new RuntimeException("Failed to send email. Please try again later.", e);
		}
	}

	public boolean verifyOtp(String otp) {
		Optional<Otprequests> request = otpRepository.findByOtp(otp);

		if (request.isPresent()) {
			Otprequests resetRequest = request.get();
			if (resetRequest.getCreated_at().isAfter(LocalDateTime.now().minusMinutes(10))) {
				this.mail = resetRequest.getEmail();
				return true;
			} else {
				throw new IllegalArgumentException("OTP has expired.");
			}
		} else {
			throw new IllegalArgumentException("Invalid OTP.");
		}
	}

	public void resetPassword(String newPassword) {
		if (this.mail == null) {
			throw new IllegalStateException("OTP verification is not initiated. Please request a password reset first.");
		}

		Optional<User> userOptional = userRepository.findByEmail(this.mail);

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			user.setPassword(passwordEncoder.encode(newPassword));
			user.setUpdatedAt(LocalDateTime.now());
			userRepository.save(user);

			otpRepository.deleteByEmail(this.mail);
			this.mail = null;
		} else {
			throw new IllegalArgumentException("Email not found");
		}
	}
}
