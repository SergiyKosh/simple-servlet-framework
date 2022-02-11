package ua.simpleservletframework.data.annotation.processor;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import ua.simpleservletframework.data.annotation.annotation.ComponentDao;

import java.util.Set;

public class ComponentDaoAnnotationProcessor {
    Set<Class<?>> findAllDao() {
        return new Reflections(ClasspathHelper.forClassLoader()).getTypesAnnotatedWith(ComponentDao.class);
    }


}
