package com.galua.onlinestore.offerservice.controllers;

import com.galua.onlinestore.offerservice.entities.Characteristics;
import com.galua.onlinestore.offerservice.services.CharacteristicsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.NoSuchElementException;

@Log
@RestController
public class CharacteristicsController {
    @Autowired
    private CharacteristicsService characteristicsService;

    @GetMapping("characteristics/{id}")
    public ResponseEntity<Characteristics> getCharacteristicsByID(@PathVariable("id") int id) {
        try {
            Characteristics characteristic = characteristicsService.getCharacteristicByID(id);
            log.severe("Характеристика найдена успешно");
            return new ResponseEntity<>(characteristic, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.severe("Характеристика не найдена");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("characteristics")
    public ResponseEntity<List<Characteristics>> getAllCharacteristics() {
        List<Characteristics> list = characteristicsService.getAllCharacteristics();
        log.severe("Получены все характеристики");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("characteristics")
    public ResponseEntity addCharacteristics(@RequestBody Characteristics characteristic, UriComponentsBuilder builder) {
        try {
            characteristicsService.createCharacteristic(characteristic);
        }
        catch(IllegalArgumentException e){
            log.severe("Попытка добавления существующей характеристики");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(Exception e){
            log.severe("Передана неверная характеристика");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/characteristics/{id}").buildAndExpand(characteristic.getId()).toUri());
        log.severe("Характеристика добавлена успешно");
        return new ResponseEntity(characteristic, headers, HttpStatus.CREATED);
    }

    @PutMapping("characteristics/{id}")
    public ResponseEntity<Characteristics> updateCharacteristics(@PathVariable(value = "id") int id,
                                                       @RequestBody Characteristics characteristic) {
        try {
            characteristicsService.updateCharacteristic(id, characteristic);
            log.severe("Характеристика обновлена успешно");
            characteristic.setId(id);
            return new ResponseEntity<>(characteristic, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            log.severe("Передана несуществующая характеристика");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            log.severe("Передана неверная характеристика");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("characteristics/{id}")
    public ResponseEntity<Void> deleteCharacteristics(@PathVariable("id") int id) {
        try {
            characteristicsService.deleteCharacteristic(id);
            log.severe("Характеристика удалена успешно");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(NoSuchElementException e){
            log.severe("Характеристика не найдена");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IllegalArgumentException e){
            log.severe("Характеристика связана с оффером");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}