package com.example.auth.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.auth.entity.ResourceOwner;
import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.AuthService;

public class AuthTokenFilter extends OncePerRequestFilter {

	private AuthService authService;
	private UserRepository userRepository;

	public AuthTokenFilter(AuthService authService, UserRepository userRepository) {
		this.authService = authService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = getToken(request);
		boolean valid = authService.isTokenValid(token);
		if (valid) {
			authenticate(token);
		}

		filterChain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		String type = "Bearer ";

		if (token == null || token.isEmpty() || !token.startsWith(type))
			return null;

		return token.substring(type.length(), token.length());
	}

	private void authenticate(String token) {
		Long userId = authService.getUserId(token);
		Optional<User> optional = userRepository.findById(userId);
		User user = optional.get();
		ResourceOwner owner = new ResourceOwner(user);
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(owner, null, owner.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
