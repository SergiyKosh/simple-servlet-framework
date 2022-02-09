package ua.simpleservletframework.core.annotation.processor.controller;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import ua.simpleservletframework.core.annotation.annotation.controller.Controller;
import ua.simpleservletframework.core.annotation.annotation.controller.RestController;
import ua.simpleservletframework.core.util.PropertyUtil;

import java.util.HashSet;
import java.util.Set;

public class ControllerAnnotationProcessor {
    private Set<Class<?>> getAllControllers() {
        return new Reflections(ClasspathHelper.forClassLoader()).getTypesAnnotatedWith(Controller.class);
    }

    private Set<Class<?>> getAllRestControllers() {
        return new Reflections(ClasspathHelper.forClassLoader()).getTypesAnnotatedWith(RestController.class);
    }

    public Set<Class<?>> getAllControllerClasses() {
        Set<Class<?>> controllerClasses = new HashSet<>();
        controllerClasses.addAll(getAllControllers());
        controllerClasses.addAll(getAllRestControllers());
        return controllerClasses;
    }
}
