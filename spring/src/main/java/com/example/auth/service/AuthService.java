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

import com.example.auth.entity.ResourceOwner;
import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Value("${jwt.expiration}")
	private String expiration;

	@Value("${jwt.secret}")
	private String secret;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByCredentials_Email(username);

		if (user.isPresent()) {
			return new ResourceOwner(user.get());
		}

		throw new UsernameNotFoundException("Invalid data!");
	}

	public String buildToken(Authentication authentication) {

		ResourceOwner owner = (ResourceOwner) authentication.getPrincipal();
		Date today = new Date();
		Date expireDate = new Date(today.getTime() + Long.parseLong(expiration));

		return Jwts.builder()
				.setIssuer("Auth Api")
				.setSubject(owner.getId().toString())
				.setIssuedAt(today)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();

	}

	public boolean isTokenValid(String token) {
		try {
			
			if (token == null || token.isEmpty())
				return false;
			
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isTokenExpired(String token) {
		
		if (token == null || token.isEmpty())
			return true;
		
		Date expirationDate = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
		if (expirationDate.before(new Date())) 
			return true;
		
		return false;
	}

	public Long getUserId(String token) {
		return Long.parseLong(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject());
	}

}
