package com.nttdata.rrss.Controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.nttdata.rrss.Services.UserService;
import org.springframework.security.core.GrantedAuthority;

@RestController
public class AuthController {
	
	private final UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/api/login")
	public ResponseEntity<JsonElement> login(@RequestBody HashMap<String, String> httpEntity, HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {

		JSONObject object = new JSONObject(httpEntity);
		String email = object.getString("username");

		User user = (User) userService.loadUserByUsername(email);
		
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		String access_token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		
		HashMap<String, String> token = new HashMap<>();
		token.put("access_token", access_token);
		token.put("user_id", this.userService.findByEmail(user.getUsername()).getId().toString());
		
		 return ResponseEntity.ok().body(new Gson().toJsonTree(token));
	}
}
