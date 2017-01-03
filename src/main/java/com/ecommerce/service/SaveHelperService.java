package com.ecommerce.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SaveHelperService {
    public static <T> T save(Class<T> clazz, String fieldName, String fieldValue, T instance, JpaRepository<T, Long> repo)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        fieldName = StringUtils.capitalize(fieldName);
        Method method = clazz.getMethod("set" + fieldName, String.class);

        method.invoke(instance, fieldValue);

        return repo.save(instance);
    }
}
