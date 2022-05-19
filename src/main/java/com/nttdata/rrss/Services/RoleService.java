package com.nttdata.rrss.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nttdata.rrss.Entity.Role;
import com.nttdata.rrss.Repository.RoleRepository;

@Service
public class RoleService {

	private final RoleRepository roleRepo;

	public RoleService(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}
	
	public Role saveRole(Role role) {
		return roleRepo.save(role);
	}
	
	public List<Role> findRoles(){
		return roleRepo.findAll();
	}
	
	public Role findRoleByName(String name) {
		return roleRepo.findByName(name);
	}
	
	public void deleteAllRoles() {
		roleRepo.deleteAll();
	}
	
	public void deleteRoleById(Long id) {
		roleRepo.deleteById(id);
	}
}
