package com.ecommerce.web;

import com.ecommerce.domain.Product;
import com.ecommerce.domain.User;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("/dashboard/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "")
    @ResponseBody
    public Product addProduct(@ModelAttribute Product product) {
        User loggedInUser = userService.getLoggedInUser();

        product.setUser(loggedInUser);

        return productService.save(product);
    }

    @PostMapping(value = "{productId}")
    @ResponseBody
    public Product updateProduct(@PathVariable Long productId, @RequestParam String fieldName, @RequestParam String fieldValue) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Product productToUpdate = productService.findOne(productId);

        return productService.updateField(productToUpdate, fieldName, fieldValue);
    }

    @GetMapping(value = "{productId}")
    public String editProduct(@PathVariable Long productId, ModelMap model) {
        Product productToEdit = productService.findOne(productId);
        model.put("product", productToEdit);

        return "product";
    }

    @PostMapping(value = "{productId}/edit")
    public String editProduct(@PathVariable Long productId, ModelMap model, @ModelAttribute("product") Product product) {

        product.setUser(userService.getLoggedInUser());
        productService.save(product);

        model.put("product", product);

        return "redirect:/dashboard/products/" + product.getId();
    }

    @PostMapping(value = "{productId}/delete")
    public String deleteProduct(@PathVariable Long productId, ModelMap model) {

        Product productToDelete = productService.findOne(productId);

        // remove product from the user
        productToDelete.getUser().getProducts().remove(productToDelete);

        // delete the product
        productService.delete(productToDelete);

        return "redirect:/dashboard";
    }
}
