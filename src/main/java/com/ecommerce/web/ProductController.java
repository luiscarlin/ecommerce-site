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

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Product addProduct() {
        User loggedInUser = userService.getLoggedInUser();

        Product productToAdd = new Product();
        productToAdd.setUser(loggedInUser);

        return productService.save(productToAdd);
    }

    @RequestMapping(value = "{productId}", method = RequestMethod.POST)
    @ResponseBody
    public Product updateProduct(@PathVariable Long productId, @RequestParam String fieldName, @RequestParam String fieldValue) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Product productToUpdate = productService.findOne(productId);

        return productService.updateField(productToUpdate, fieldName, fieldValue);
    }

    @RequestMapping(value = "{productId}", method = RequestMethod.GET)
    public String editProduct(@PathVariable Long productId, ModelMap model) {
        Product productToEdit = productService.findOne(productId);
        model.put("product", productToEdit);

        return "product";
    }

    @RequestMapping(value = "{productId}/edit", method = RequestMethod.POST)
    public String editProduct(@PathVariable Long productId, ModelMap model, @ModelAttribute("product") Product product) {

        product.setUser(userService.getLoggedInUser());
        productService.save(product);

        model.put("product", product);

        return "redirect:/dashboard/products/" + product.getId();
    }

    @RequestMapping(value = "{productId}/delete", method = RequestMethod.POST)
    public String deleteProduct(@PathVariable Long productId, ModelMap model) {

        Product productToDelete = productService.findOne(productId);

        // remove product from the user
        productToDelete.getUser().getProducts().remove(productToDelete);

        // delete the product
        productService.delete(productToDelete);

        return "redirect:/dashboard";
    }
}
