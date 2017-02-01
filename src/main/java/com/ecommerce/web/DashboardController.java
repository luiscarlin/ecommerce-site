package com.ecommerce.web;

import com.ecommerce.domain.Product;
import com.ecommerce.domain.User;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class DashboardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/dashboard")
    public String getDashboard(Model model) {
        User foundUser = userService.getLoggedInUser();

        List<Product> products = productService.findByUser(foundUser);
        LOGGER.debug("Loading number of products={} for user={} into the view", products.size(), foundUser.getEmail());

        model.addAttribute("products", products);

        return "dashboard";
    }
}
