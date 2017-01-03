package com.ecommerce.service;

import com.ecommerce.domain.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findOne(Long productId) throws IllegalArgumentException {
        return productRepository.findOne(productId);
    }

    @Override
    public Product updateField(Product product, String fieldName, String fieldValue)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return SaveHelperService.save(Product.class, fieldName, fieldValue, product, productRepository);
    }
}
