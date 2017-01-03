package com.ecommerce.service;

import com.ecommerce.domain.Product;

import java.lang.reflect.InvocationTargetException;

public interface ProductService {
    Product save(Product product);

    Product findOne(Long productId) throws IllegalArgumentException;

    Product updateField(Product product, String fieldName, String fieldValue)throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}
