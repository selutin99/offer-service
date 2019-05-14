package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.customerservice.dto.AuthenticationRequestDto;
import com.galua.onlinestore.customerservice.entities.PaidType;
import com.galua.onlinestore.offerservice.entities.Offers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    @Autowired
    private RestTemplate myRestTemplate;

    @Autowired
    private OffersService offersService;

    @Value("${service.customer.url}")
    private String serviceURL;

    @Value("${service.customer.email}")
    private String email;

    @Value("${service.customer.password}")
    private String password;

    public String getToken(){
        AuthenticationRequestDto requestDto = new AuthenticationRequestDto();
        requestDto.setEmail(email);
        requestDto.setPassword(password);

        Map response = myRestTemplate.postForObject(
                serviceURL+"auth/login", requestDto, Map.class);
        return response.get("token").toString();
    }

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
