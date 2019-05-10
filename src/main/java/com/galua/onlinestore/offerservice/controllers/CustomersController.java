package com.galua.onlinestore.offerservice.controllers;

import com.galua.onlinestore.offerservice.entities.Offers;
import com.galua.onlinestore.offerservice.services.CustomerService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@Log
@RestController
public class CustomersController {
    @Autowired
    CustomerService customerService;

    @Autowired
    private RestTemplate myRestTemplate;

    @GetMapping("/offers/paidtype/{id}")
    public ResponseEntity<List<Offers>> getPaidTypeOfCustomer(@PathVariable("id") int id) {
        try {
            List<Offers> offersList = customerService.getPaidTypes(id);
            log.severe("Типы возвращены успешно");
            return new ResponseEntity<>(offersList, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            log.severe("Заказчик не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
