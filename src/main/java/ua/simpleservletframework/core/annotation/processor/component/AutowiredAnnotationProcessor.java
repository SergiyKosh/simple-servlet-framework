package ua.simpleservletframework.core.annotation.processor.component;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import ua.simpleservletframework.core.annotation.annotation.component.Autowired;
import ua.simpleservletframework.mvc.annotation.annotation.controller.RestController;
import ua.simpleservletframework.core.beans.BeanImplementation;
import ua.simpleservletframework.core.context.Context;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static org.reflections.util.ClasspathHelper.forClassLoader;

public class AutowiredAnnotationProcessor {
    private Set<Field> autowiredFields() {
        return new Reflections(forClassLoader()).getFieldsAnnotatedWith(Autowired.class);
    }

    private Map.Entry<String, ?> searchBean(Field field) {
        field.setAccessible(true);
        return BeanImplementation.getBean(field.getType());
    }

    public void injectBeans() {
        Set<Class<?>> getAllRestControllers = new Reflections(ClasspathHelper.forClassLoader()).getTypesAnnotatedWith(RestController.class);
        getAllRestControllers.forEach(controller -> {
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
