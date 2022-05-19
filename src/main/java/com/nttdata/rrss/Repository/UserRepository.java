package com.nttdata.rrss.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.rrss.Entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	UserEntity findByEmail(String email);
}
