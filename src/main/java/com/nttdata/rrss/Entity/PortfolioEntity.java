package com.nttdata.rrss.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "Portfolio", schema="portfolio-manager")
public class PortfolioEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private Long author;

    @Column( length = 100000 )
    private ArrayList composition = new ArrayList();

    public PortfolioEntity() {
    }
}
