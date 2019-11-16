package com.example.auth.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Value("${expiration}")
	private String expiration;

	@Value("${secret}")
	private String secret;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByCredentials_Email(username);

		if (user.isPresent()) {
			return user.get();
		}

		throw new UsernameNotFoundException("Invalid data!");
	}

	public String buildToken(Authentication authentication) {

		User loggedUser = (User) authentication.getPrincipal();
		Date today = new Date();
		Date expireDate = new Date(today.getTime() + Long.parseLong(expiration));

		return Jwts.builder().setIssuer("Auth Api").setSubject(loggedUser.getId().toString()).setIssuedAt(today)
				.setExpiration(expireDate).signWith(SignatureAlgorithm.HS256, secret).compact();

	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getUserId(String token) {
		return Long.parseLong(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject());
	}

}
