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
    private CategoriesRepo categoriesRepository;

    @Override
    public void createCategory(Categories category) {
        if(category==null){
            log.severe("Была передана пустая категория");
            throw new IllegalArgumentException("Категория не передана");
        }
        List<Categories> list = categoriesRepository.findByName(category.getName());
        if (list.size() > 0) {
            log.severe("Была передана существующая категория");
            throw new IllegalArgumentException("Категория уже существует");
        }
        else {
            categoriesRepository.save(category);
            log.severe("Сохранение категории: " +category);
        }
    }

    @Override
    public void updateCategory(int id, Categories category) {
        Categories findCategory = getCategoryByID(id);
        findCategory.setName(category.getName());

        List<Categories> list = categoriesRepository.findByName(category.getName());
        if(category.getName().equals(findCategory.getName())) {
            list.remove(findCategory);
        }
        if(list.size()>0){
            throw new IllegalArgumentException("Категория уже существует");
        }
        categoriesRepository.save(findCategory);

        log.severe("Обновление категории: "+findCategory);
    }

    @Override
    public void deleteCategory(int id) {
        log.severe("Удаление категории с id="+id);
        categoriesRepository.delete(getCategoryByID(id));
    }

    @Override
    public Categories getCategoryByID(int id) {
        log.severe("Получение категории с id="+id);
        return categoriesRepository.findById(id).get();
    }

    @Override
    public List<Categories> getAllCategory() {
        log.severe("Получение всех категорий");
        List<Categories> listOfCategories = new ArrayList<>();
        categoriesRepository.findAll().forEach(e -> listOfCategories.add(e));
        return listOfCategories;
    }
}
