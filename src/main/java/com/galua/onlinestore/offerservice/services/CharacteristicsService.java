package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.offerservice.entities.Characteristics;

import java.util.List;

public interface CharacteristicsService {
    void createCharacteristic(Characteristics characteristic);
    Characteristics updateCharacteristic(int id, Characteristics characteristic);
    void deleteCharacteristic(int id);

    Characteristics getCharacteristicByID(int id);
    List<Characteristics> getAllCharacteristics();
}
