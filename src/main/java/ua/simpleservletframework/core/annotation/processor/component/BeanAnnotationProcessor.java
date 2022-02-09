package ua.simpleservletframework.core.annotation.processor.component;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import ua.simpleservletframework.core.annotation.annotation.component.Component;
import ua.simpleservletframework.core.annotation.annotation.controller.Controller;
import ua.simpleservletframework.core.annotation.annotation.controller.RestController;
import ua.simpleservletframework.core.beans.BeanImplementation;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class BeanAnnotationProcessor {
    public Set<Class<?>> getAllBeanClasses() {
        Reflections reflections = new Reflections(ClasspathHelper.forClassLoader());
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        Set<Class<?>> restControllers = reflections.getTypesAnnotatedWith(RestController.class);
        Set<Class<?>> components = reflections.getTypesAnnotatedWith(Component.class);
        Set<Class<?>> beans = new HashSet<>();
        components.addAll(controllers);
        components.addAll(restControllers);
        components.addAll(beans);
        return components;
    }

    private Set<?> getAllBeanFields() {
        return new Reflections(ClasspathHelper.forClassLoader()).getFieldsAnnotatedWith(Component.class);
    }

    public void initBeans(Set<Class<?>> beans) {
        BeanImplementation beanImplementation = new BeanImplementation();
        beans.forEach(bean -> {
            try {
                if (bean.isAnnotationPresent(Component.class)) {
                    beanImplementation.initBean(bean.getAnnotation(Component.class).value(), bean);
                } else if (bean.isAnnotationPresent(RestController.class)) {
                    beanImplementation.initBean(bean.getAnnotation(RestController.class).value(), bean);
                } else if (bean.isAnnotationPresent(Controller.class)) {
                    beanImplementation.initBean(bean.getAnnotation(Controller.class).value(), bean);
                }
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
