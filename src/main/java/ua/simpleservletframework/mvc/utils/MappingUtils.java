package ua.simpleservletframework.mvc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import ua.simpleservletframework.core.context.Context;
import ua.simpleservletframework.mvc.annotation.annotation.controller.Controller;
import ua.simpleservletframework.mvc.annotation.annotation.controller.RestController;
import ua.simpleservletframework.mvc.annotation.annotation.mapping.GetMapping;
import ua.simpleservletframework.mvc.annotation.annotation.url.PathVariable;
import ua.simpleservletframework.mvc.servlet.DispatcherServlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static ua.simpleservletframework.mvc.servlet.DispatcherServlet.request;
import static ua.simpleservletframework.mvc.servlet.DispatcherServlet.response;


public class MappingUtils {
    public void mappingHandler(Object result, Class<?> controller) throws IOException, ServletException {
        if (result.toString().startsWith("redirect:")) {
            result = result.toString()
                    .replaceAll("redirect:", "")
                    .replaceAll(".jsp", "")
                    .replaceAll(".html", "");
            response.sendRedirect(result.toString());
        } else {
            if (controller.isAnnotationPresent(RestController.class)) {
                response.setContentType("application/json; UTF-8");
                ObjectMapper mapper = new ObjectMapper();
                String returned = mapper.writeValueAsString(result);
                response.getWriter().print(returned);
            } else if (controller.isAnnotationPresent(Controller.class)) {
                if (!result.toString().endsWith(".jsp")) {
                    result += ".jsp";
                }
                request.getRequestDispatcher(result.toString()).forward(request, response);
            }
        }
    }

    public static Map<String, String> getPVValues(String uri) {
        String[] uriArr = uri.split("/");
        String[] reqUriArr = DispatcherServlet.request.getRequestURI().split("/");
        Map<String, String> pathVariables = new HashMap<>();

        for (int i = 0; i < uriArr.length; i++) {
            if (!reqUriArr[i].equals(uriArr[i])) {
                pathVariables.put(uriArr[i].substring(1, uriArr[i].length() - 1), reqUriArr[i]);
            }
        }

        return pathVariables;
    }

    public static Object getMappingMethodResult(
            Context<?> context,
            Class<?> controller,
            Method mapping
    ) throws InvocationTargetException, IllegalAccessException {
        String[] pathVars = getPVValues(mapping.getAnnotation(GetMapping.class).value())
                .values()
                .toArray(new String[0]);
        Object result;

        if (mapping.getParameters().length != 0) {
            result = mapping.invoke(context.getBean(controller).getValue(), pathVars);
        } else {
            result = mapping.invoke(context.getBean(controller).getValue());
        }

        return result;
    }
}
