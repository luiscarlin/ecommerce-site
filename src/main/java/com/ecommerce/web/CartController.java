package com.ecommerce.web;

import com.ecommerce.domain.Cart;
import com.ecommerce.domain.Product;
import com.ecommerce.domain.User;
import com.ecommerce.service.CartService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.SecurityService;
import com.ecommerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    SecurityService securityService;

    @Autowired
    ProductService productService;

    @GetMapping(value = "")
    public String getCart(ModelMap model) {

        User user = userService.getLoggedInUser();

        if (user == null) {
            return null;
        }

        Cart cart = user.getCart();

        if (cart == null) {
            cart = userService.createCartForUser(user.getId());
        }

        model.put("cart", cart);

        return "cart";
    }

    @GetMapping(value = "/products")
    @ResponseBody
    public Cart getCartItems() {

        User user = userService.getLoggedInUser();

        if (user == null) {
            LOGGER.info("The user has not logged in");
            return null;
        }

        return user.getCart();
    }

    @PostMapping(value = "/products/{productId}")
    @ResponseBody
    public Cart addProductToCart(@PathVariable Long productId) {

        LOGGER.info("Adding product={} to user cart", productId);

        if (securityService.isAnonymous()) {
            LOGGER.info("The user has not logged in");
            return null;
        }

        User user = userService.getLoggedInUser();
        Cart userCart = user.getCart();

        Set<Product> productsInCart = new HashSet<Product>();

        if (userCart == null) {
            LOGGER.info("The user cart does not exist. Creating one.");

            userCart = userService.createCartForUser(user.getId());

            LOGGER.info("user={} now has cartId={}", user.getEmail(), user.getCart().getId());
            LOGGER.info("cartId={} now has user={}", userCart.getId(), userCart.getUser().getEmail());
        }

        productsInCart.addAll(userCart.getProducts());

        LOGGER.info("user={} had these products in cart={}", user.getEmail(),
                productsInCart.stream().map(prod -> prod.getId().toString()).collect(Collectors.joining(",")));

        // associate the product to the user cart
        Product productToAdd = productService.findOne(productId);
        productToAdd.getCarts().add(userCart);
        productToAdd = productService.save(productToAdd);
        LOGGER.info("carts associated to this product={}",
                productToAdd.getCarts().stream().map(cart -> cart.getId().toString()).collect(Collectors.joining(",")));

        // associate the user cart to the product
        productsInCart.add(productToAdd);
        userCart.setProducts(productsInCart);
        userCart.setDateAdded(new Date());
        userCart = cartService.save(userCart);
        LOGGER.info("products associated to this cart={}",
                userCart.getProducts().stream().map(prod -> prod.getId().toString()).collect(Collectors.joining(",")));

        return userCart;
    }
}
