package ua.simpleservletframework.core.beans;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class BeanImplementation<T> {
    private static final Map<String, Object> beans = new HashMap<>();

    public void initBean(String id, Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!beans.containsKey(id)) {
            beans.put(id, clazz.getConstructor().newInstance());
        } else {
            throw new RuntimeException("Bean with id " + id + " is already exists!");
        }
    }

    @SuppressWarnings("all")
    public static<T> T getBean(String id) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T instance = (T) beans.get(id);
        if (instance == null) {
            synchronized (instance.getClass()) {
                if (instance == null) {
                    beans.put(id, instance.getClass().getConstructor().newInstance());
                    instance = (T) beans.get(id);
                }
            }
        }
        return instance;
    }
}
