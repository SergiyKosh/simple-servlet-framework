package ua.simpleservletframework.core.context;

import ua.simpleservletframework.core.beans.BeanImplementation;

import java.lang.reflect.InvocationTargetException;

public class Context<T> {
    public T getBean(String id) {
        try {
            return BeanImplementation.getBean(id);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
