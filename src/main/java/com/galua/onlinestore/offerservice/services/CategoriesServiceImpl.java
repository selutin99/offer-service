package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.offerservice.entities.Categories;
import com.galua.onlinestore.offerservice.repositories.CategoriesRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class CategoriesServiceImpl implements CategoriesService{

    @Autowired
    private CategoriesRepo categoriesRepositoty;

    @Override
    public void createCategory(Categories category) {
        if(category==null){
            log.severe("Была передана пустая категория");
            throw new IllegalArgumentException("Категория не передана");
        }
        List<Categories> list = categoriesRepositoty.findByName(category.getName());
        if (list.size() > 0) {
            log.severe("Была передана существующая категория");
            throw new IllegalArgumentException("Категория уже существует");
        }
        else {
            log.severe("Сохранение категории: " +category);
            categoriesRepositoty.save(category);
        }
    }

    @Override
    public void updateCategory(Categories category) {
        log.severe("Обновление категории: "+category);
        categoriesRepositoty.save(category);
    }

    @Override
    public void deleteCategory(int id) {
        log.severe("Удаление категории с id="+id);
        categoriesRepositoty.delete(getCategoryByID(id));
    }

    @Override
    public Categories getCategoryByID(int id) {
        log.severe("Получение категории с id="+id);
        return categoriesRepositoty.findById(id).get();
    }

    @Override
    public List<Categories> getAllCategory() {
        log.severe("Получение всех категорий");
        List<Categories> listOfCategories = new ArrayList<>();
        categoriesRepositoty.findAll().forEach(e -> listOfCategories.add(e));
        return listOfCategories;
    }
}
