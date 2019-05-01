package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.offerservice.entities.Offers;

import java.util.List;

public interface OffersService {
    void createOffer(Offers offer);
    void updateOffer(int id, Offers offer);
    void deleteOffer(int id);

    Offers getOfferByID(int id);
    List<Offers> getAllOffers();
}
