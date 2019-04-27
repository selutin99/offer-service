package com.galua.onlinestore.offerservice.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="characteristics")
public class Characteristics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "characteristics")
    private Set<Offers> offers = new HashSet<>();

    public Characteristics(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
