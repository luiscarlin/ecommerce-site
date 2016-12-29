package com.ecommerce.validator;

import com.ecommerce.domain.User;
import com.ecommerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserValidator.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        LOGGER.debug("Validating user with email={}", user.getEmail());

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (user.getEmail().length() < 3 || user.getEmail().length() > 32) {
            errors.rejectValue("email", "Size.userForm.email");
            LOGGER.warn("Email length validation failed for user with email={}", user.getEmail());
        }

        User userFound = null;
        try {
            userFound = userService.findByEmail(user.getEmail());
        }
        catch(UsernameNotFoundException e) {
            LOGGER.debug("The user with email={} was not found. That's good!", user.getEmail());
        }

        if (userFound != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
            LOGGER.warn("Validation failed because there is a user with email={}", user.getEmail());
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 3 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
            LOGGER.warn("Password length validation failed user with email={}", user.getEmail());
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
            LOGGER.warn("Validation failed because passwords did not match for user with email={}", user.getEmail());
        }
    }
}