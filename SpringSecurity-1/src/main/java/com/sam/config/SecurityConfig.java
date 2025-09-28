package com.sam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.sam.service.AuthenticationSuccessHandler;
import com.sam.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		return httpSecurity.csrf(Customizer->Customizer.disable())
				           .authorizeHttpRequests(request->{
				        	   request.requestMatchers("/","/api/**","/register/**").permitAll()
				        	          .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
				        	          .requestMatchers("/admin/**").hasRole("ADMIN")
				        	          .anyRequest().authenticated();
				           }).formLogin(Customizer->{
				        	   Customizer.loginPage("/login")
				        	             .successHandler(new AuthenticationSuccessHandler())
				        	             .permitAll();
				           })
				           .build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(myUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	
	
	/*
	@Bean
	public UserDetailsService userDetailsService()
	{
		UserDetails user = User.builder()
				               .username("ANIKET")
				               .password("$2a$12$HSpb5cSR1FN9ScrOs/xwQeVt4AxNlUR/CHIO1FvnDgNwN2ZAdUlUK")
				               .roles("USER")
				               .build();
		
		UserDetails admin = User.builder()
	                            .username("SHYAM")
	                            .password("$2a$12$HSpb5cSR1FN9ScrOs/xwQeVt4AxNlUR/CHIO1FvnDgNwN2ZAdUlUK")
	                            .roles("ADMIN")
	                            .build();
		
		return new InMemoryUserDetailsManager(user,admin);
	}*/
}
