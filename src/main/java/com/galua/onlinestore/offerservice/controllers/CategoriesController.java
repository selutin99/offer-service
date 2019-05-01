package com.galua.onlinestore.offerservice.controllers;

import com.galua.onlinestore.offerservice.entities.Categories;
import com.galua.onlinestore.offerservice.services.CategoriesService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.NoSuchElementException;

@Log
@RestController
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("categories/{id}")
    public ResponseEntity<Categories> getCategoriesByID(@PathVariable("id") int id) {
        try {
            Categories category = categoriesService.getCategoryByID(id);
            log.severe("Категория найдена успешно");
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.severe("Категория не найдена");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("categories")
    public ResponseEntity<List<Categories>> getAllCategories() {
        List<Categories> list = categoriesService.getAllCategory();
        log.severe("Получены все категории");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("categories")
    public ResponseEntity addCategories(@RequestBody Categories category, UriComponentsBuilder builder) {
        try {
            categoriesService.createCategory(category);
        }
        catch(IllegalArgumentException e){
            log.severe("Попытка добавления существующей категории");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(Exception e){
            log.severe("Передана неверная категория");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/categories/{id}").buildAndExpand(category.getId()).toUri());
        log.severe("Категория добавлена успешно");
        return new ResponseEntity(category, headers, HttpStatus.CREATED);
    }

    @PutMapping("categories/{id}")
    public ResponseEntity<Categories> updateCategories(@PathVariable(value = "id") int id,
                                                   @RequestBody Categories category) {
        try {
            categoriesService.updateCategory(id, category);
            log.severe("Категория обновлена успешно");
            category.setId(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            log.severe("Передана несуществующая категория");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            log.severe("Передана неверная категория");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("categories/{id}")
    public ResponseEntity<Void> deleteCategories(@PathVariable("id") int id) {
        try {
            categoriesService.deleteCategory(id);
            log.severe("Категория удалена успешно");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(NoSuchElementException e){
            log.severe("Категория не найдена");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}