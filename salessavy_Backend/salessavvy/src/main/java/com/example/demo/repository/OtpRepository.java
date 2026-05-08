package com.example.demo.repository;

import java.util.Optional; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Otprequests;

import jakarta.transaction.Transactional;

@Repository
public interface OtpRepository extends JpaRepository<Otprequests, Integer>{
	// Find by email
	Optional<Otprequests> findByEmail(String email);
	
	// Delete by email
	@Modifying
	@Transactional
	void deleteByEmail(String email);
	
	// Find by OTP
	Optional<Otprequests> findByOtp(String otp);
	
	@Override
    default void delete(Otprequests entity) {
        // Optionally implement this method if needed
    }
	
	// Add a method to check if OTP is verified for the given email
    @Modifying
    @Transactional
    boolean existsByEmail(String email); // Checks if OTP exists for the email, implying OTP verification
}
