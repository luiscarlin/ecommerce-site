package com.ecommerce.service;

import com.ecommerce.domain.Product;
import com.ecommerce.domain.User;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

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
    public Page<Product> findAll(Integer page, Integer size) {
        return productRepository.findAll(new PageRequest(page, size));
    }

    @Override
    public Product updateField(Product product, String fieldName, String fieldValue)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return SaveHelperService.save(Product.class, fieldName, fieldValue, product, productRepository);
    }

    @Override
    public List<Product> findByUser(User user) {
        return productRepository.findByUser(user);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
