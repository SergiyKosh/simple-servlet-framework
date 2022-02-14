package ua.simpleservletframework.mvc.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.simpleservletframework.mvc.annotation.processor.controller.ControllerAnnotationProcessor;
import ua.simpleservletframework.mvc.annotation.processor.mapping.MappingAnnotationProcessor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import static ua.simpleservletframework.mvc.utils.MappingUtils.getResourcePath;
import static ua.simpleservletframework.mvc.utils.MappingUtils.setImage;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    public static volatile HttpServletRequest request;
    public static volatile HttpServletResponse response;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().endsWith(".jpg")) {
            String path;
            String[] uri = request.getRequestURI().split("/");
            response.setContentType("image/jpeg");
            path = getResourcePath(uri);
            setImage(path, response.getOutputStream(), getServletContext().getResourceAsStream(path));
        } else {
            Set<Class<?>> controllers = new ControllerAnnotationProcessor().getAllControllerClasses();
            try {
                new MappingAnnotationProcessor().mappingHandler(controllers, request, response);
            } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | ServletException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


