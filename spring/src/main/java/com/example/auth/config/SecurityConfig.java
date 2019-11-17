package com.example.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.auth.filter.AuthTokenFilter;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.AuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// Configurações de autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());
	}

	// Configurações de autorização
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/auth/login")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.csrf()
			.disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(new AuthTokenFilter(authService, userRepository), UsernamePasswordAuthenticationFilter.class);
	}

	// Configurações de recursos estáticos (js, css, imagens, etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("Auth Api"));
//	}

}
