package ua.simpleservletframework.mvc.annotation.processor.mapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import ua.simpleservletframework.core.context.Context;
import ua.simpleservletframework.mvc.annotation.annotation.controller.Controller;
import ua.simpleservletframework.mvc.annotation.annotation.controller.RestController;
import ua.simpleservletframework.mvc.annotation.annotation.mapping.*;
import ua.simpleservletframework.mvc.annotation.annotation.url.PathVariable;
import ua.simpleservletframework.mvc.servlet.DispatcherServlet;
import ua.simpleservletframework.mvc.utils.MappingUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ua.simpleservletframework.core.util.Constants.*;
import static ua.simpleservletframework.mvc.utils.MappingUtils.*;
import static ua.simpleservletframework.mvc.utils.RequestMethod.*;
import static ua.simpleservletframework.mvc.utils.UriUtils.*;

public class MappingAnnotationProcessor {
    private final Context<?> context = new Context<>();
    private final MappingUtils mappingUtils = new MappingUtils();

    private Set<Class<?>> getRequiredControllers(Set<Class<?>> controllers, HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        if (requestUri.length() > 1 && requestUri.endsWith("/")) {
            requestUri = requestUri.substring(0, requestUri.length() - 1);
        }

        String finalRequestUri = requestUri;
        return controllers.stream()
                .filter(c -> {
                    if (c.isAnnotationPresent(Controller.class)) {
                        if (finalRequestUri.startsWith(c.getAnnotation(Controller.class).value())) {
                            return MappingUtils.getRequiredControllers(
                                    c, finalRequestUri,
                                    c.getAnnotation(Controller.class).value()
                            );
                        } else {
                            return false;
                        }
                    } else if (c.isAnnotationPresent(RestController.class)) {
                        if (finalRequestUri.startsWith(c.getAnnotation(RestController.class).value())) {
                            return MappingUtils.getRequiredControllers(
                                    c, finalRequestUri,
                                    c.getAnnotation(RestController.class).value()
                            );
                        } else {
                            return false;
                        }
                    } else {
                        throw new RuntimeException(UNKNOWN_CONTROLLER_TYPE);
                    }
                })
                .collect(Collectors.toSet());
    }

    private Map.Entry<? extends Class<?>, Method> getRequiredMethod(Set<Class<?>> requiredControllers, HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        if (requestUri.length() > 1 && requestUri.endsWith("/")) {
            requestUri = requestUri.substring(0, requestUri.length() - 1);
        }
        String finalRequestUri = requestUri;
        return requiredControllers.stream()
                .map(c -> {
                    Method mapping = Arrays.stream(c.getDeclaredMethods())
                            .filter(m -> {
                                if (c.isAnnotationPresent(Controller.class)) {
                                    return MappingUtils.getRequiredMethod(m, finalRequestUri, c.getAnnotation(Controller.class).value());
                                } else if (c.isAnnotationPresent(RestController.class)) {
                                    return MappingUtils.getRequiredMethod(m, finalRequestUri, c.getAnnotation(RestController.class).value());
                                } else {
                                    throw new RuntimeException(MULTIPLE_CONTROLLER_TYPE_EXCEPTION);
                                }
                            }).filter(m -> {
                                if (request.getMethod().equals(GET)) {
                                    return m.isAnnotationPresent(GetMapping.class);
                                } else if (request.getMethod().equals(POST)) {
                                    return m.isAnnotationPresent(PostMapping.class);
                                } else if (request.getMethod().equals(PUT)) {
                                    return m.isAnnotationPresent(PutMapping.class);
                                } else if (request.getMethod().equals(DELETE)) {
                                    return m.isAnnotationPresent(DeleteMapping.class);
                                } else if (request.getMethod().equals(OPTIONS)) {
                                    return m.isAnnotationPresent(OptionsMapping.class);
                                } else {
                                    return m.isAnnotationPresent(PatchMapping.class);
                                }
                            })
                            .findFirst()
                            .orElseThrow(RuntimeException::new);
                    return new HashMap.SimpleEntry<>(c, mapping);
                })
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private void getMappingHandler(
            Class<?> controller,
            Method mapping
    ) {
        try {
            Object result = getMappingMethodResult(context, controller, mapping);
            mappingUtils.mappingHandler(result, controller);
        } catch (IllegalAccessException | InvocationTargetException | ServletException
                | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void otherRequestTypesHandler(
            Class<?> controller,
            Method mapping
    ) throws InvocationTargetException, IllegalAccessException, IOException, ServletException {
        Object result = getMappingMethodResult(context, controller, mapping);
        mappingUtils.mappingHandler(result, controller);
    }

    @SneakyThrows
    public void mappingHandler(Set<Class<?>> controllers, HttpServletRequest request, HttpServletResponse response) {
        DispatcherServlet.request = request;
        DispatcherServlet.response = response;

        Set<Class<?>> requiredControllers = getRequiredControllers(controllers, request);
        if (requiredControllers.isEmpty()) {
            response.getWriter().write(REQUEST_TYPE_NOT_SUPPORTED_ON_THIS_URL + request.getRequestURI());
        } else {
            Map.Entry<? extends Class<?>, Method> mappingMethod = getRequiredMethod(requiredControllers, request);
            Class<?> controller = mappingMethod.getKey();
            Method mapping = mappingMethod.getValue();
            if (mapping.isAnnotationPresent(GetMapping.class)) {
                getMappingHandler(controller, mapping);
            } else if (
                    mapping.isAnnotationPresent(PutMapping.class)
                            || mapping.isAnnotationPresent(PostMapping.class)
                            || mapping.isAnnotationPresent(DeleteMapping.class)
                            || mapping.isAnnotationPresent(PatchMapping.class)
                            || mapping.isAnnotationPresent(OptionsMapping.class)
            ) {
                otherRequestTypesHandler(controller, mapping);
            }
        }
    }
}
