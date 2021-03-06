package com.nttdata.rrss.Entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "portfolios", schema = "portfolio-manager")
public class PortfolioEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    private Long author;

    @Column(length = 190000000)
    private ArrayList<Object> composition;

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

    public ArrayList<Object> getComposition() {
        return composition;
    }

    public void setComposition(ArrayList<Object> composition) {
        this.composition = composition;
    }
}
