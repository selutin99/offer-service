package com.galua.onlinestore.offerservice.repositories;

import com.galua.onlinestore.offerservice.entities.Characteristics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacteristicsRepo extends CrudRepository<Characteristics, Integer> {
    List<Characteristics> findByName(String name);
    List<Characteristics> findByDescription(String description);

    List<Characteristics> findByNameAndDescription(String name, String description);
}
