package com.ecommerce.repository;

import com.ecommerce.doimain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
