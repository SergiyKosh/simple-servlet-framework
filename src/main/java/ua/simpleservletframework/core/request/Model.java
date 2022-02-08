package ua.simpleservletframework.core.request;

import ua.simpleservletframework.core.servlet.DispatcherServlet;

import javax.annotation.Nullable;
import java.util.Map;

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
}
