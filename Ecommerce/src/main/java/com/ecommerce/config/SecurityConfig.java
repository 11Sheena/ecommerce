package com.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.services.UserService;


@Configuration
@EnableWebSecurity

public class SecurityConfig {
	@Autowired
	private UserService userService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
	{
		
		
		http.csrf(t -> t.disable())
				.authorizeHttpRequests((requests) -> requests.requestMatchers("/", "/web/**","/api-docs","/swagger-ui/**","/v3/api-docs/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/cart/**").hasRole("CUSTOMER")
						.requestMatchers("/orders/**").hasAnyRole("CUSTOMER","ADMIN")

						.anyRequest().authenticated())
				.exceptionHandling(t->t.accessDeniedPage("/web/accessDenied"))
				.formLogin(t->{
					t.usernameParameter("email");
					t.passwordParameter("password");
				})
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
