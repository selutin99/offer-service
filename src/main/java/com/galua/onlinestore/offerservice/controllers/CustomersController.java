package com.galua.onlinestore.offerservice.controllers;

import com.galua.onlinestore.customerservice.dto.AuthenticationRequestDto;
import com.galua.onlinestore.offerservice.entities.Offers;
import com.galua.onlinestore.offerservice.services.CustomerService;
import com.galua.onlinestore.offerservice.services.OffersService;
import com.galua.onlinestore.offerservice.services.OrderService;
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
    OffersService offersService;

    @Autowired
    OrderService orderService;

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

    @PostMapping("/ordered/{id}")
    public ResponseEntity<Map<Object, Object>> makeOrder(@PathVariable int id,
                                                         @RequestBody AuthenticationRequestDto requestDto) {
        Offers offer;
        String token;
        try {
            token = customerService.getToken(requestDto);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            offer = offersService.getOfferByID(id);
        }
        catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Map<Object, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("offer", offer);

        if(orderService.makeOrder(response)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
