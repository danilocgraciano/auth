package com.example.auth.controller.dto;

import com.example.auth.entity.User;

public class UserDto {

	private Long id;

	private String email;

	public UserDto(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

}
