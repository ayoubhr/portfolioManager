package com.nttdata.rrss.Controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.nttdata.rrss.Entity.UserEntity;
import com.nttdata.rrss.Services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	private final UserService userService;
	//private final RoleService roleService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/save-user")
	public ResponseEntity<JsonElement> saveUser(@RequestBody Map<String, Object> payload) throws Exception {
		URI uri = URI.create(
				ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save-user").toUriString());
		return ResponseEntity.created(uri).body(new Gson().toJsonTree(userService.saveUser(payload)));
	}
	
	@PutMapping("/edit-user-email")
	public ResponseEntity<JsonElement> editUserEmail(@RequestBody Map<String, Object> payload) throws Exception {
		URI uri = URI.create(
				ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/edit-user-email").toUriString());
		return ResponseEntity.created(uri).body(new Gson().toJsonTree(userService.editUserEmail(payload)));
	}
	
	@PutMapping("/edit-user-avatar")
	public ResponseEntity<JsonElement> editUserAvatar(@RequestBody Map<String, Object> payload) throws Exception {
		URI uri = URI.create(
				ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/edit-user-avatar").toUriString());
		return ResponseEntity.created(uri).body(new Gson().toJsonTree(userService.editUserAvatar(payload)));
	}
	
	@GetMapping("/find-user")
	@ResponseBody
	public JsonElement getUser(@RequestParam Long id) {
		return new Gson().toJsonTree(userService.findUser(id));
	}
	
	@GetMapping("/find-users")
	public ResponseEntity<List<UserEntity>> getUsers() {
		return ResponseEntity.ok().body(userService.findUsers());
	}
}
