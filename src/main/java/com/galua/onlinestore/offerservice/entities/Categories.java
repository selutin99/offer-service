package com.galua.onlinestore.offerservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@EqualsAndHashCode(exclude = "offers")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Offers> offers;

    public Categories(String name, Offers... offers) {
        this.name = name;

        this.offers = Stream.of(offers).collect(Collectors.toList());
        this.offers.forEach(x -> x.setCategory(this));
    }
}
