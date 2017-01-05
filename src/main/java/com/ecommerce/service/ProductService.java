package com.ecommerce.service;

import com.ecommerce.domain.Product;
import com.ecommerce.domain.User;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ProductService {
    Product save(Product product);
    Product findOne(Long productId) throws IllegalArgumentException;
    Product updateField(Product product, String fieldName, String fieldValue)throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
    List<Product> findByUser(User user);
}
