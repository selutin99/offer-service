package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.offerservice.entities.Characteristics;

import java.util.List;

public interface CharacteristicsService {
    void createCharacteristic(Characteristics characteristic);
    void updateCharacteristic(Characteristics characteristic);
    void deleteCharacteristic(int id);

    Characteristics getCharacteristicByID(int id);
    List<Characteristics> getAllCharacteristics();
}
