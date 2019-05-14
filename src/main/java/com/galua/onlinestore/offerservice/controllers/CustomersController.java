package com.galua.onlinestore.offerservice.controllers;

import com.galua.onlinestore.offerservice.entities.Offers;
import com.galua.onlinestore.offerservice.services.CustomerService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Log
@RestController
public class CustomersController {
    @Autowired
    CustomerService customerService;

    @Autowired
    private RestTemplate myRestTemplate;

    @GetMapping("/offers/paidtype/{id}")
    public ResponseEntity<List<Offers>> getPaidTypeOfCustomer(@PathVariable("id") int id,
                                                              @RequestHeader("Authorization") String token) {
        try {
            List<Offers> offersList = customerService.getPaidTypes(id, token);
            log.severe("Типы возвращены успешно");
            return new ResponseEntity<>(offersList, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            log.severe("Заказчик не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            log.severe("Неверный запрос");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/token")
    public ResponseEntity<Map<Object, Object>> getTokenForOrder() {
        try {
            String token = customerService.getToken();
            Map<Object, Object> response = new HashMap<>();
            response.put("token", token);

            log.severe("Токен возвращен успешно");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e){
            log.severe("Неверный запрос");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
