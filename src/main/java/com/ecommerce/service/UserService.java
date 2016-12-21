package com.ecommerce.service;

import com.ecommerce.domain.User;

public interface UserService {
    void save(User user);
    User findByEmail(String email);
}
