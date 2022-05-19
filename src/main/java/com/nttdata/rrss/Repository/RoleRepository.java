package com.nttdata.rrss.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.rrss.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
	void deleteById(Long id);
}
