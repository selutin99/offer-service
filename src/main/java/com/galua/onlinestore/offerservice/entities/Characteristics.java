package com.galua.onlinestore.offerservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = "offers")
@ToString(exclude = "offers")
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
    private List<Offers> offers = new ArrayList<>();

    public Characteristics(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
