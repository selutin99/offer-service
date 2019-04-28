package com.galua.onlinestore.offerservice.repositories;

import com.galua.onlinestore.offerservice.entities.Categories;
import com.galua.onlinestore.offerservice.entities.Offers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffersRepo extends CrudRepository<Offers, Integer> {
    List<Offers> findByName(String name);

    List<Offers> findByCategory(Categories category);
}

