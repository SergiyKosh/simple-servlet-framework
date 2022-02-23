package ua.simpleservletframework.core.beans;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import ua.simpleservletframework.core.annotation.Autowired;
import ua.simpleservletframework.core.annotation.Component;
import ua.simpleservletframework.core.annotation.Service;
import ua.simpleservletframework.core.context.Context;
import ua.simpleservletframework.mvc.annotation.annotation.controller.Controller;
import ua.simpleservletframework.mvc.annotation.annotation.controller.RestController;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AutowiredAnnotationProcessor {
    private Map.Entry<String, ?> searchBean(Field field) {
        field.setAccessible(true);
        return BeanImplementation.getBean(field);
    }

    public void injectBeans() {
        Reflections reflections = new Reflections(ClasspathHelper.forClassLoader());
        Set<Class<?>> getRestControllers = reflections.getTypesAnnotatedWith(RestController.class);
        Set<Class<?>> getControllers = reflections.getTypesAnnotatedWith(Controller.class);
        Set<Class<?>> getComponents = reflections.getTypesAnnotatedWith(Component.class);
        Set<Class<?>> getServices = reflections.getTypesAnnotatedWith(Service.class);
        Set<Class<?>> getAllControllers = new HashSet<>();

        getAllControllers.addAll(getRestControllers);
        getAllControllers.addAll(getControllers);
        getAllControllers.addAll(getComponents);
        getAllControllers.addAll(getServices);

        getAllControllers.forEach(controller -> {
            if (controller.getDeclaredFields().length != 0) {
                Arrays.stream(controller.getDeclaredFields())
                        .forEach(field -> {
                            field.setAccessible(true);
                            Map.Entry<String, ?> bean = searchBean(field);
                            Map.Entry<String, ?> instanceForBean = new Context<>().getBean(field.getDeclaringClass());
                            if (field.isAnnotationPresent(Autowired.class)) {
                                try {
                                    field.set(instanceForBean.getValue(), bean.getValue());
                                    System.out.println();
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
            }
        });
    }
}
