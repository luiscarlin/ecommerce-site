package com.ecommerce.web;

import com.ecommerce.domain.User;
import com.ecommerce.service.SecurityService;
import com.ecommerce.service.UserService;
import com.ecommerce.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping(value = "/register")
    public String registerGet(Model model) {
        model.addAttribute("userForm", new User());

        return "register";
    }

    @PostMapping(value = "/register")
    public String registerPost(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        LOGGER.debug("Validation errors={}", bindingResult.getAllErrors().toString());

        if (bindingResult.hasErrors()) {
            LOGGER.warn("Failed with {}", bindingResult.getErrorCount());
            return "register";
        }

        try {
            LOGGER.debug("Persisting user with email={}", userForm.getEmail());
            userService.saveNew(userForm);

        } catch (DataIntegrityViolationException e) {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            bindingResult.reject("email", "Email already exists");
            return "register";
        }

        securityService.autologin(userForm.getEmail(), userForm.getPasswordConfirm());

        return "redirect:/dashboard";
    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {

        if (error != null)
            model.addAttribute("error", "Provide valid email and password");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully");

        return "login";
    }
}
