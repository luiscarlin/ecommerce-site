package com.ecommerce.web;

import com.ecommerce.domain.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class StoreController {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(ModelMap model) {

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

    @PostMapping(value = "/products/{productId}")
    public @ResponseBody String addProductToCart(@PathVariable Long productId, ModelMap model) {

        return "{\"success\": true}";
    }
}
