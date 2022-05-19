package com.nttdata.rrss.Security;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration 
@EnableWebSecurity 
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	private final UserDetailsService userDS;
	
	public SecurityConfig(UserDetailsService userDS) {
		this.userDS = userDS;
	}
	
	// This method loads the provided user details for further use in authentication. 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDS).passwordEncoder(bCryptPasswordEncoder);
	}

	// This method specifies authorization for routes and authenticates user + checks if user is authorized to access the specified route.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//http.formLogin();
		
		// login route
		http.authorizeHttpRequests().antMatchers("/api/login").permitAll();
		
		// user routes
		http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/v1/users/save-user").permitAll();
		http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/v1/users/find-user").hasAnyAuthority("ROLE_USER");
		http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/v1/users/find-users").hasAnyAuthority("ROLE_USER");
		
		// portfolio routes
		http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/v1/portfolios/**").hasAnyAuthority("ROLE_USER");
		http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/v1/portfolios/**").hasAnyAuthority("ROLE_USER");
		http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE, "/api/v1/portfolios/delete-portfolio").hasAnyAuthority("ROLE_USER");
		http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE, "/api/v1/portfolios/delete-portfolio-author").hasAnyAuthority("ROLE_ADMIN");
		
		// reaction routes
		http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/v1/reactions/**").hasAnyAuthority("ROLE_USER");
		http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/v1/reactions/**").hasAnyAuthority("ROLE_USER");
		http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE, "/api/v1/reactions/**").hasAnyAuthority("ROLE_USER");
		
		http.authorizeHttpRequests().anyRequest().authenticated();
		http.addFilter(new CustomAuthFilter(authenticationManagerBean()));
		
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.cors();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
