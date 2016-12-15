package com.ecommerce.repository;

import com.ecommerce.doimain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
