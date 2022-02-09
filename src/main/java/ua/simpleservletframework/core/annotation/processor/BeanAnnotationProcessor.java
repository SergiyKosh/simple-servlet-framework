package ua.simpleservletframework.core.annotation.processor;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import ua.simpleservletframework.core.annotation.annotation.component.Bean;
import ua.simpleservletframework.core.beans.BeanImplementation;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class BeanAnnotationProcessor {
    public Set<Class<?>> getAllBeanClasses() {
        return new Reflections(ClasspathHelper.forClassLoader()).getTypesAnnotatedWith(Bean.class);
    }

    private Set<?> getAllBeanFields() {
        return new Reflections(ClasspathHelper.forClassLoader()).getFieldsAnnotatedWith(Bean.class);
    }

    public void initAllBeans(Set<Class<?>> beans) {
        BeanImplementation beanImplementation = new BeanImplementation();
        beans.forEach(bean -> {
            try {
                beanImplementation.initBean(bean.getAnnotation(Bean.class).value(), bean);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
