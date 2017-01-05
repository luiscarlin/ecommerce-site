package com.ecommerce.web;

import com.ecommerce.domain.Product;
import com.ecommerce.domain.User;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/dashboard"}, method = RequestMethod.GET)
    public String dashboard(Model model) {
        //=> where the problem is!!
        User foundUser = userService.getLoggedInUser();

        System.out.println(">>user found: " + foundUser.getEmail());

        List<Product> products = productService.findByUser(foundUser);

        System.out.println(">>list of products found: ");
        products.forEach(product -> System.out.println(product.getId()));


        model.addAttribute("products", products);

        return "dashboard";
    }
}
