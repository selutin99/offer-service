package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.offerservice.entities.Characteristics;
import com.galua.onlinestore.offerservice.repositories.CharacteristicsRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class CharacteristicsServiceImpl implements CharacteristicsService{

    @Autowired
    private CharacteristicsRepo characteristicsRepositoty;

    @Autowired
    private OffersService offersService;

    @Override
    public void createCharacteristic(Characteristics characteristic) {
        if(characteristic==null){
            log.severe("Была передана пустая характеристика");
            throw new IllegalArgumentException("Характеристика не передана");
        }
        List<Characteristics> list = characteristicsRepositoty.findByName(characteristic.getName());
        if (list.size() > 0) {
            log.severe("Была передана существующая характеристика");
            throw new IllegalArgumentException("Характеристика уже существует");
        }
        else {
            characteristicsRepositoty.save(characteristic);
            log.severe("Сохранение характеристики: " +characteristic);
        }
    }

    @Override
    public Characteristics updateCharacteristic(int id, Characteristics characteristic) {
        Characteristics findCharacteristics = getCharacteristicByID(id);
        findCharacteristics.setName(characteristic.getName());
        findCharacteristics.setDescription(characteristic.getDescription());

        List<Characteristics> list = characteristicsRepositoty.findByName(characteristic.getName());
        if(characteristic.getName().equals(findCharacteristics.getName())) {
            list.remove(findCharacteristics);
        }
        if(list.size()>0){
            throw new IllegalArgumentException("Характеристика уже существует");
        }
        characteristicsRepositoty.save(findCharacteristics);

        log.severe("Обновление характеристики: "+findCharacteristics);
        return findCharacteristics;
    }

    @Override
    public void deleteCharacteristic(int id) {
        log.severe("Удаление характеристики с id="+id);
        if(getCharacteristicByID(id).getOffers().isEmpty()) {
            characteristicsRepositoty.delete(getCharacteristicByID(id));
        }
        else{
            throw new IllegalArgumentException("Характеристика связана с оффером");
        }
    }

    @Override
    public Characteristics getCharacteristicByID(int id) {
        log.severe("Получение характеристики с id="+id);
        return characteristicsRepositoty.findById(id).get();
    }

    @Override
    public List<Characteristics> getAllCharacteristics() {
        log.severe("Получение всех характеристик");
        List<Characteristics> listOfCharacteristics = new ArrayList<>();
        characteristicsRepositoty.findAll().forEach(e -> listOfCharacteristics.add(e));
        return listOfCharacteristics;
    }
}