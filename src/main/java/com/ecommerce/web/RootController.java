package com.ecommerce.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @GetMapping(value = "/")
    public String root(ModelMap model) {
        return "redirect:/store";
    }
}
