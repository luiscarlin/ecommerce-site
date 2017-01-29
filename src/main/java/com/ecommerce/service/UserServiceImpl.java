package com.ecommerce.service;

import com.ecommerce.domain.Cart;
import com.ecommerce.domain.Role;
import com.ecommerce.domain.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import javafx.scene.canvas.GraphicsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CartService cartService;

    @Override
    public User save(User user) {
        LOGGER.debug("Persisting user with email={}", user.getEmail());

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<Role>(roleRepository.findAll()));

        User savedUser = userRepository.save(user);

        if (savedUser != null && (savedUser.getEmail().equals(user.getEmail()))) {
            LOGGER.debug("User with email={} was persisted successfully!", user.getEmail());
        }
        LOGGER.warn("User with email={} was not persisted", user.getEmail());

        return savedUser;
    }

    @Override
    public User findByEmail(String email) throws UsernameNotFoundException {
        LOGGER.debug("Searching for user with email={}", email);
        User foundUser = userRepository.findByEmail(email);

        if (foundUser == null) {
            LOGGER.warn("User with email={} was not found", email);
            throw new UsernameNotFoundException(String.format("User with email=%s was not found", email));
        }
        return foundUser;
    }

    @Override
    public User getLoggedInUser() throws UsernameNotFoundException {
        String loggedInEmail = securityService.findLoggedInUsername();

        if (loggedInEmail == null)  {
            LOGGER.warn("There is no logged in user at the moment");
            return null;
        }

        User loggedInUser = userRepository.findByEmail(loggedInEmail);

        if (loggedInUser == null) {
            LOGGER.warn("User with email={} was not found", loggedInEmail);
            throw new UsernameNotFoundException(String.format("User with email=%s was not found", loggedInEmail));
        }

        return loggedInUser;
    }

    @Override
    public Cart createCartForUser(Long userId) {
        User user = userRepository.findOne(userId);
        Cart cart = new Cart();

        user.setCart(cart);
        cart.setUser(user);

        cart = cartService.save(cart);
        save(user);

        return cart;
    }
}
