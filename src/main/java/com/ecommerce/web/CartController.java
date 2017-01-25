package com.ecommerce.web;

import com.ecommerce.domain.Cart;
import com.ecommerce.domain.User;
import com.ecommerce.service.SecurityService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CartController {

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @PostMapping(value = "/cart/products/{productId}")
    public @ResponseBody Cart addProductToCart(@PathVariable Long productId, ModelMap model) {

        if (securityService.isAnonymous()) {
            return null;
        }

        return new Cart();
    }
}
