package ua.simpleservletframework.core.beans;

import ua.simpleservletframework.core.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class BeanImplementation<T> {
    private static final Map<String, Object> beans = new HashMap<>();

    public void initBean(String id, Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!beans.containsKey(id)) {
            beans.put(id, clazz.getConstructor().newInstance());
        } else {
            throw new RuntimeException("Bean with id " + id + " already exists!");
        }
    }

    @SuppressWarnings("all")
    public static<T> T getBean(String id) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T instance = (T) beans.get(id);
        if (instance == null) {
            synchronized (instance.getClass()) {
                if (instance == null) {
                    beans.put(id, (T) instance.getClass().getConstructor().newInstance());
                    instance = (T) beans.get(id);
                }
            }
        }
        return instance;
    }

    public static Map.Entry<String, ?> getBean(Field field) {
        return beans.entrySet().stream()
                .filter(bean -> bean.getKey().equals(field.getAnnotation(Autowired.class).value())
                || bean.getValue().getClass().getTypeName().equals(field.getType().getTypeName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot inject bean. Required bean not found"));
    }

    @SuppressWarnings("all")
    public static Map.Entry<String, ?> getBean(Class<?> type) {
        return beans.entrySet().stream()
                .filter(bean -> bean.getValue().getClass().getTypeName().equals(type.getTypeName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot inject bean. Required bean not found"));
    }
}
