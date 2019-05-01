package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.offerservice.entities.Categories;

import java.util.List;

public interface CategoriesService {
    void createCategory(Categories category);
    void updateCategory(int id, Categories category);
    void deleteCategory(int id);

    Categories getCategoryByID(int id);
    List<Categories> getAllCategory();
}
