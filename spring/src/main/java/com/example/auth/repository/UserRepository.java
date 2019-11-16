package com.example.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

//	@Query("from User u where u.credentials.email = :email")
	Optional<User> findByCredentials_Email(String email);

}
