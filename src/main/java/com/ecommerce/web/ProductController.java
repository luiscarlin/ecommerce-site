package com.ecommerce.web;

import com.ecommerce.domain.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("/dashboard/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Product addProduct() {
        Product productToAdd = new Product();
        return productService.save(productToAdd);
    }

    @RequestMapping(value = "{productId}", method = RequestMethod.POST)
    @ResponseBody
    public Product updateProduct(@PathVariable Long productId, @RequestParam String fieldName, @RequestParam String fieldValue) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Product productToUpdate = productService.findOne(productId);

        return productService.updateField(productToUpdate, fieldName, fieldValue);
    }
}
