package com.nttdata.rrss.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nttdata.rrss.Entity.Role;
import com.nttdata.rrss.Entity.UserEntity;
import com.nttdata.rrss.Repository.RoleRepository;
import com.nttdata.rrss.Repository.UserRepository;

@Service
@Transactional
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepo;
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public UserService(UserRepository userRepository, RoleRepository roleRepo) {
		this.userRepository = userRepository;
		this.roleRepo = roleRepo;
	}
	
	public HashMap<String, String> saveUser(Map<String, Object> payload) {
		UserEntity user = new UserEntity();
		Role role = new Role();
		Collection<Role> roles = new ArrayList<>();
		HashMap<String, String> response = new HashMap<>();
		
		try {
			role.setId(roleRepo.findByName("ROLE_USER").getId());
			role.setName(roleRepo.findByName("ROLE_USER").getName());
			roles.add(role);
			
			for (int i = 0; i < 1; i++) {
				user.setAvatar((String) payload.get("avatar"));
				user.setEmail((String) payload.get("email"));
				user.setLastName((String) payload.get("lastname"));
				user.setUserName((String) payload.get("username"));
				user.setPassword(bCryptPasswordEncoder.encode((String) payload.get("password")));
				user.setBirth_date((String) payload.get("birthDate"));
				user.setRoles(roles);
			}
			
			if(userRepository.save(user) != null) {
				response.put("email", (String) payload.get("email"));
			}  else {
				throw new Exception("Something went wrong");
			}
		} catch (Exception e) {
			response.put("403", "Bad Request");
			response.put("error", e.getMessage());
		}
		return response;
	}
	
	public HashMap<String, String> editUserEmail(Map<String, Object> payload) {
		HashMap<String, String> response = new HashMap<>();
		Long id = Long.parseLong((String)payload.get("id"));
		Optional<UserEntity> u = userRepository.findById(id);
		UserEntity user = u.get();
		
		user.setEmail((String)payload.get("email"));
		
		if(userRepository.save(user) != null) {
			response.put("new_email", user.getEmail());
		}
		return response;
	}
	
	public HashMap<String, String> editUserAvatar(Map<String, Object> payload) {
		HashMap<String, String> response = new HashMap<>();
		Long id = Long.parseLong((String)payload.get("id"));
		Optional<UserEntity> u = userRepository.findById(id);
		UserEntity user = u.get();
		
		user.setAvatar((String)payload.get("avatar"));
		
		if(userRepository.save(user) != null) {
			response.put("new_avatar", user.getAvatar());
		}
		return response;
	}
	
	public List<UserEntity> findUsers(){
		return userRepository.findAll();
	}
	
	public HashMap<String, Object> findUser(Long id) {
		
		Optional<UserEntity> u = userRepository.findById(id);
		UserEntity user = u.get();
		
		HashMap<String, Object> objectNode = new HashMap<String, Object>();
		objectNode.put("id", user.getId());
	    objectNode.put("avatar", user.getAvatar());
	    objectNode.put("username", user.getUserName());
	    objectNode.put("lastname", user.getLastName());
	    objectNode.put("email", user.getEmail());
	    if(user.getDate() != null) {
	    	objectNode.put("date", user.getDate().toString().substring(0, 10));
	    }
	    if(user.getBirth_date() != null) {
	    	objectNode.put("birthDate", user.getBirth_date());
	    }
	    return objectNode;
	}
	
	public void addRoleToUser(String email, String roleName) {
		UserEntity user = userRepository.findByEmail(email);
		Role role = roleRepo.findByName(roleName);
		user.getRoles().add(role);
	}

	// Provides the user for authentication and authorization.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("User couldn't be found");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>(); 
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new User(user.getEmail(), user.getPassword(), authorities);
	}

	public UserEntity findByEmail(String username) {
		return userRepository.findByEmail(username);
	}
}
