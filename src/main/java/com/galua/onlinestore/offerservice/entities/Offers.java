package com.galua.onlinestore.offerservice.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "characteristics")
@Entity
@Table(name="offers")
public class Offers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private float price;

    //TODO: Добавить внешний ключ после подключения модуля
    private int paidTypeID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Categories category;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "characteristics_offers",
               joinColumns = @JoinColumn(name = "characteristics_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "offer_id", referencedColumnName = "id"))
    private Set<Characteristics> characteristics;

    public Offers(String name, float price, int paidTypeID, Categories category, Characteristics... characteristics) {
        this.name = name;
        this.price = price;
        this.paidTypeID = paidTypeID;
        this.category = category;

        this.characteristics = Stream.of(characteristics).collect(Collectors.toSet());
        this.characteristics.forEach(x -> x.getOffers().add(this));
    }
}