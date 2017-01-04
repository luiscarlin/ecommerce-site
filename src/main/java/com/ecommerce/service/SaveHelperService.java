package com.ecommerce.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SaveHelperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveHelperService.class);

    public static <T> T save(Class<T> clazz, String fieldName, String fieldValue, T instance, JpaRepository<T, Long> repo)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException {

        fieldName = StringUtils.capitalize(fieldName);

        String setterMethod = "set" + fieldName;

        Method[] methods = clazz.getMethods();

        T persisted = null;

        for (Method method : methods) {

            if (method.getName().equals(setterMethod)) {

                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    LOGGER.debug("The setter method={} does not take exactly one parameter", setterMethod);
                    continue;
                }

                Class<?> paramType = parameterTypes[0];
                LOGGER.debug("Inspecting method={} with parameter type={}", method.getName(), paramType.getName());

                if (paramType.getName().contains(Double.class.getSimpleName())) {
                    fieldValue = fieldValue.trim().replace("$", "");
                    method.invoke(instance, Double.valueOf(fieldValue));
                    persisted = repo.save(instance);

                }
                else if (paramType.getName().contains(String.class.getSimpleName())) {
                    method.invoke(instance,fieldValue);
                    persisted = repo.save(instance);
                }
                else  {
                    LOGGER.debug("The setter method={} with parameter type={} is not supported", setterMethod, paramType.getName());
                    continue;
                }
                break;
            }
        }

        if (persisted == null) {
            LOGGER.warn("The setter method={} to use with parameter={} was not found", setterMethod, fieldValue);
            throw new NoSuchMethodException(String.format("The setter method=%s to use with parameter=%s was not found",
                    setterMethod, fieldValue));
        }

        return persisted;
    }
}
