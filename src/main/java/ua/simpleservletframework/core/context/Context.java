package ua.simpleservletframework.core.context;

import ua.simpleservletframework.core.beans.BeanImplementation;

import java.lang.reflect.Field;
import java.util.Map;

public class Context<T> {
    @SuppressWarnings("all")
    public Map.Entry<String, T> getBean(Class<?> type) {
        return (Map.Entry<String, T>) BeanImplementation.getBean(type);
    }

    @SuppressWarnings("all")
    public Map.Entry<String, T> getBean(Field field) {
        return (Map.Entry<String, T>) BeanImplementation.getBean(field);
    }
}
