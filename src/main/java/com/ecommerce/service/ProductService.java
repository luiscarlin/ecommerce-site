package com.ecommerce.service;

import com.ecommerce.domain.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Product save(Product product);
}
