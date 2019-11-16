package com.example.auth.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired
	private UserRepository repository;

	@PostMapping
	@Transactional
	public User save(@Valid User user) {
		return repository.save(user);
	}

	@PutMapping("/{id}")
	@Transactional
	public void update(@PathVariable Long id, User user) {
		user.setId(id);
		repository.save(user);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@GetMapping("/{id}")
	public User one(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("User not found " + id, 1));
	}

	@GetMapping
	public List<User> all() {
		return repository.findAll();
	}

}
