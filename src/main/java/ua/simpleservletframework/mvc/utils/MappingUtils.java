package ua.simpleservletframework.mvc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import ua.simpleservletframework.mvc.annotation.annotation.controller.Controller;
import ua.simpleservletframework.mvc.annotation.annotation.controller.RestController;

import java.io.IOException;

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
}
