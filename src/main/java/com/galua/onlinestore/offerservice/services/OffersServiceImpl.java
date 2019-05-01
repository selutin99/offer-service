package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.offerservice.entities.Offers;
import com.galua.onlinestore.offerservice.repositories.OffersRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class OffersServiceImpl implements OffersService{

    @Autowired
    private OffersRepo offersRepositoty;

    @Override
    public void createOffer(Offers offer) {
        if(offer==null){
            log.severe("Был передан пустой оффер");
            throw new IllegalArgumentException("Оффер не передан");
        }
        List<Offers> list = offersRepositoty.findByName(offer.getName());
        if (list.size() > 0) {
            log.severe("Был передан существующий оффер");
            throw new IllegalArgumentException("Оффер уже существует");
        }
        else {
            offersRepositoty.save(offer);
            log.severe("Сохранение оффера: " +offer);
        }
    }

    @Override
    public void updateOffer(int id, Offers offer) {
        Offers findOffer = getOfferByID(id);
        findOffer.setName(offer.getName());

        List<Offers> list = offersRepositoty.findByName(offer.getName());
        if(offer.getName().equals(findOffer.getName())) {
            list.remove(findOffer);
        }
        if(list.size()>0){
            throw new IllegalArgumentException("Оффер уже существует");
        }
        offersRepositoty.save(findOffer);

        log.severe("Обновление оффера: "+findOffer);
    }

    @Override
    public void deleteOffer(int id) {
        log.severe("Удаление оффера с id="+id);
        offersRepositoty.delete(getOfferByID(id));
    }

    @Override
    public Offers getOfferByID(int id) {
        log.severe("Получение оффера с id="+id);
        return offersRepositoty.findById(id).get();
    }

    @Override
    public List<Offers> getAllOffers() {
        log.severe("Получение всех офферов");
        List<Offers> listOfOffers = new ArrayList<>();
        offersRepositoty.findAll().forEach(e -> listOfOffers.add(e));
        return listOfOffers;
    }
}

