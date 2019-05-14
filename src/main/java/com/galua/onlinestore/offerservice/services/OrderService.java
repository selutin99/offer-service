package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.offerservice.entities.Categories;
import com.galua.onlinestore.offerservice.entities.Offers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private RestTemplate myRestTemplate;

    @Value("${service.order.url}")
    private String serviceURL;

    public boolean makeOrder(Map<Object, Object> response) {
        ResponseEntity<Void> res = myRestTemplate.postForEntity(
                serviceURL+"orders/make",
                response,
                Void.class);
        return res.getStatusCodeValue()==201;
    }
}
