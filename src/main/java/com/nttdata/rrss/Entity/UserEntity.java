package com.nttdata.rrss.Entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "User", schema="rrss_db")
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String username) {
		this.username = username;
	}
	public String getLastName() {
		return lastname;
	}
	public void setLastName(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
}
