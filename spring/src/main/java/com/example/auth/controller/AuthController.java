package com.example.auth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.controller.dto.TokenDto;
import com.example.auth.entity.Credentials;
import com.example.auth.service.AuthService;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthService authService;

	@PostMapping
	public ResponseEntity<TokenDto> auth(@RequestBody @Valid Credentials credentials) {

		try {
			UsernamePasswordAuthenticationToken data = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());

			Authentication authentication = authenticationManager.authenticate(data);

			String token = authService.buildToken(authentication);

			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}

	}

	
}
