package ua.simpleservletframework.core.ui;

import jakarta.servlet.http.HttpServletRequest;
import ua.simpleservletframework.core.servlet.DispatcherServlet;

import javax.annotation.Nullable;
import java.util.*;

public class Model {
    public void addAttribute(String name, @Nullable Object value) {
        DispatcherServlet.request.setAttribute(name, value);
    }

    public void addAllAttributes(Map<String, ?> attributes) {
        attributes.forEach((key, value) -> DispatcherServlet.request.setAttribute(key, value));
    }

    public Object getAttribute(String name) {
        return DispatcherServlet.request.getAttribute(name);
    }

    public Enumeration<String> getAttributeNames() {
        return DispatcherServlet.request.getAttributeNames();
    }

    public List<?> getAllAttributes() {
        List<Object> attributes = new ArrayList<>();
        Enumeration<String> names = getAttributeNames();
        HttpServletRequest request = DispatcherServlet.request;
        do {
            attributes.add(request.getAttribute(names.nextElement()));
        } while (names.hasMoreElements());

        return attributes;
    }

    public Map<String, ?> getAllAttributesAsMap() {
        Map<String, Object> attributes = new HashMap<>();
        Enumeration<String> names = getAttributeNames();
        HttpServletRequest request = DispatcherServlet.request;

        do {
            attributes.put(names.nextElement(), request.getAttribute(names.nextElement()));
        } while (names.hasMoreElements());

        return attributes;
    }
}
