package ua.simpleservletframework.core.beans.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class BeanImplementation<T> {
    private static final Map<String, Object> beanInstances = new HashMap<>();

    public void initBean(String id, Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!beanInstances.containsKey(id)) {
            beanInstances.put(id, clazz.getConstructor().newInstance());
        } else {
            throw new RuntimeException("Bean with id " + id + " is already exists!");
        }
    }

    public static<T> T getBean(String id) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T instance = (T) beanInstances.get(id);
        if (instance == null) {
            synchronized (instance.getClass()) {
                if (instance == null) {
                    beanInstances.put(id, instance.getClass().getConstructor().newInstance());
                }
            }
        }
        return instance;
    }
}
