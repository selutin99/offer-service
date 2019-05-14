package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.customerservice.entities.PaidType;
import com.galua.onlinestore.offerservice.entities.Offers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private RestTemplate myRestTemplate;

    @Autowired
    private OffersService offersService;

    @Value("${service.customer.url}")
    private String serviceURL;

    public List<Offers> getPaidTypes(int id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<List<PaidType>> response = myRestTemplate.exchange(
                serviceURL+"customers/paidtype/"+id,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<PaidType>>(){});
        if(response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
            throw new IllegalArgumentException();
        }
        List<PaidType> paidTypeList = response.getBody();
        List<Offers> offerses = offersService.getAllOffers();

        List<Offers> result = new ArrayList<>();

        for(Offers offer: offerses){
            for(PaidType paidType: paidTypeList){
                if(paidType.getId()==offer.getPaidTypeID()){
                    result.add(offer);
                }
            }
        }
        return result;
    }
}
