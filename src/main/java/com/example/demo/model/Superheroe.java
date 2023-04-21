package com.example.demo.model;

import jakarta.persistence.*;
@Entity
@Table(name = "superheroes")
public class Superheroe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;

    public Superheroe() {
    }

    public Superheroe(String name) {
        this.name = name;
    }

    public Superheroe(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
