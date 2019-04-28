package com.galua.onlinestore.offerservice.repositories;

import com.galua.onlinestore.offerservice.entities.Categories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepo extends CrudRepository<Categories, Integer> {
    List<Categories> findByName(String name);
}