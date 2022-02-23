package ua.simpleservletframework.mvc.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.simpleservletframework.mvc.annotation.processor.controller.ControllerAnnotationProcessor;
import ua.simpleservletframework.mvc.annotation.processor.mapping.MappingAnnotationProcessor;

import java.util.Set;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    public static volatile HttpServletRequest request;
    public static volatile HttpServletResponse response;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        Set<Class<?>> controllers = new ControllerAnnotationProcessor().getAllControllerClasses();
        new MappingAnnotationProcessor().mappingHandler(controllers, request, response);
    }
}


