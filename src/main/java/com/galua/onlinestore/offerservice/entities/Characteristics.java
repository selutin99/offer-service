package com.galua.onlinestore.offerservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = "offers")
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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
                },
                mappedBy = "characteristics")
    private Set<Offers> offers = new HashSet<>();

    public Characteristics(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
