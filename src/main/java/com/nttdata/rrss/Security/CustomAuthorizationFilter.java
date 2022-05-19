package com.nttdata.rrss.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthorizationFilter extends OncePerRequestFilter{

	// This method checks whether user is authorized to access a specific route or not.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getServletPath().equals("/api/login")) {
			filterChain.doFilter(request, response);
		} else {
			String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if(authHeader != null && authHeader.startsWith("Bearer ")) {
				try {
					String token = authHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					
					String email = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					
					Collection<SimpleGrantedAuthority> authority = new ArrayList<>();
					Arrays.stream(roles).forEach(role -> {
						authority.add(new SimpleGrantedAuthority(role));
					});
					
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, null, authority);
					SecurityContextHolder.getContext().setAuthentication(authToken);
					
					filterChain.doFilter(request, response);
				} catch(Exception e) {
					response.setHeader("error", e.getMessage());
					response.setStatus(HttpStatus.FORBIDDEN.value());
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					//response.sendError(HttpStatus.FORBIDDEN.value());
					HashMap<String, String> error = new HashMap<>();
					error.put("error_message", e.getMessage());
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

}
