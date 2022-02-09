package ua.simpleservletframework.core.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.simpleservletframework.core.annotation.processor.BeanAnnotationProcessor;
import ua.simpleservletframework.core.annotation.processor.ControllerAnnotationProcessor;
import ua.simpleservletframework.core.annotation.processor.MappingAnnotationProcessor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        BeanAnnotationProcessor beanAnnotationProcessor = new BeanAnnotationProcessor();
        Set<Class<?>> beans = beanAnnotationProcessor.getAllBeanClasses();
        beanAnnotationProcessor.initAllBeans(beans);
    }

    public static HttpServletRequest request;
    public static HttpServletResponse response;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Set<Class<?>> controllers = new ControllerAnnotationProcessor().getAllControllerClasses();
        try {
            new MappingAnnotationProcessor().mappingHandler(controllers, request, response);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
