package com.ecommerce.service;

import com.ecommerce.domain.Product;

public interface ProductService {
    Product save(Product product);

    Product findOne(Long productId) throws IllegalArgumentException;
}
