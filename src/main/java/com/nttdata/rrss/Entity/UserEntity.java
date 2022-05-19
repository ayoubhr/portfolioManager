package com.nttdata.rrss.Entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "User", schema="portfolio-manager")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="avatar", nullable=true, unique=false)
	private String avatar;
	
	@Column(name="username", nullable=false, unique=false)
	private String username;
	
	@Column(name="lastname", nullable=false, unique=false)
	private String lastname;
	
	@Column(name="email", nullable=false, unique=true)
	private String email;
	
	@Column(name="password", nullable=false, unique=false)
	private String password;
	
	@Column(name="birth_date", nullable=true, unique=false)
	private String birth_date;
	
	@CreationTimestamp
	@Column(name="date")
    private Timestamp date;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Column(name="roles")
	private Collection<Role> roles = new ArrayList<>();

	public UserEntity() {
	}
}
