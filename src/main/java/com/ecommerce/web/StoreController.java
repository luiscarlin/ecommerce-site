package com.ecommerce.web;

import com.ecommerce.domain.Product;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/store")
public class StoreController {
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String getStore(ModelMap model) {

        Page<Product> productPage = productService.findAll(0, 10);
        model.put("products", productPage);

        return "store";
    }

    @GetMapping(value = "/products/{productId}")
    public String viewProduct(@PathVariable Long productId, ModelMap model) {

        Product productToView = productService.findOne(productId);
        model.put("product", productToView);

        return "prod_details";
    }
}
