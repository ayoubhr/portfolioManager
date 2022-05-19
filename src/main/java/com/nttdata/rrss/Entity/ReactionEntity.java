package com.nttdata.rrss.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Reactions", schema="portfolio-manager")
public class ReactionEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long user;
	
	private Long publication;
	
	private String name;

	public ReactionEntity() {
	}
}
