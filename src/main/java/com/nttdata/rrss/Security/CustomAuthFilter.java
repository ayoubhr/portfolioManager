package com.nttdata.rrss.Security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;

	public CustomAuthFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	// This method makes sure user is actually who he says he is.
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String email = request.getParameter("username");
		String password = request.getParameter("password");
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		
		return authenticationManager.authenticate(authenticationToken);
	}

	// This method creates the access_token once user is authenticated
	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		String access_token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		
		//response.setHeader("access_token", access_token);
		HashMap<String, String> token = new HashMap<>();
		token.put("access_token", access_token);
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), token);
	}
}
