package com.galua.onlinestore.offerservice.controllers;

import com.galua.onlinestore.offerservice.entities.Offers;
import com.galua.onlinestore.offerservice.services.OffersService;
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
public class OffersController {
    @Autowired
    private OffersService offersService;

    @GetMapping("offers/{id}")
    public ResponseEntity<Offers> getOffersByID(@PathVariable("id") int id) {
        try {
            Offers offer = offersService.getOfferByID(id);
            log.severe("Оффер найден успешно");
            return new ResponseEntity<>(offer, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.severe("Оффер не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("offers")
    public ResponseEntity<List<Offers>> getAllOffers() {
        List<Offers> list = offersService.getAllOffers();
        log.severe("Получены все офферы");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("offers")
    public ResponseEntity addOffers(@RequestBody Offers offers, UriComponentsBuilder builder) {
        try {
            offersService.createOffer(offers);
        }
        catch(IllegalArgumentException e){
            log.severe("Попытка добавления существующего оффера");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(Exception e){
            log.severe("Передан неверный оффер");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/offers/{id}").buildAndExpand(offers.getId()).toUri());
        log.severe("Оффер добавлен успешно");
        return new ResponseEntity(offers, headers, HttpStatus.CREATED);
    }

    @PutMapping("offers/{id}")
    public ResponseEntity<Offers> updateOffers(@PathVariable(value = "id") int id,
                                               @RequestBody Offers offer) {
        try {
            offersService.updateOffer(id, offer);
            log.severe("Оффер обновлен успешно");
            return new ResponseEntity<>(offer, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            log.severe("Передан несуществующий оффер");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            log.severe("Передан неверный оффер");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("offers/{id}")
    public ResponseEntity<Void> deleteOffers(@PathVariable("id") int id) {
        try {
            offersService.deleteOffer(id);
            log.severe("Оффер удалён успешно");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(NoSuchElementException e){
            log.severe("Оффер не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}