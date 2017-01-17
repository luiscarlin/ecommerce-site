package com.ecommerce.service;

import com.ecommerce.domain.Product;
import com.ecommerce.domain.User;
import org.springframework.data.domain.Page;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ProductService {
    Product save(Product product);
    Product findOne(Long productId) throws IllegalArgumentException;
    Page<Product> findAll(Integer page, Integer size);
    Product updateField(Product product, String fieldName, String fieldValue)throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
    List<Product> findByUser(User user);
    void delete(Product product);
}
