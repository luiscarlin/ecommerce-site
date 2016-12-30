package com.ecommerce.service;

import com.ecommerce.domain.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User save(User user);
    User findByEmail(String email) throws UsernameNotFoundException;
}
