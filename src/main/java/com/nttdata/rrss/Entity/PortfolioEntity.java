package com.nttdata.rrss.Entity;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "Portfolio", schema="rrss_db")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public ArrayList getComposition() {
        return composition;
    }

    public void setComposition(ArrayList composition) {
        this.composition = composition;
    }
}
