package ua.simpleservletframework.mvc.annotation.processor.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.simpleservletframework.mvc.annotation.annotation.controller.Controller;
import ua.simpleservletframework.mvc.annotation.annotation.controller.RestController;
import ua.simpleservletframework.core.context.Context;
import ua.simpleservletframework.mvc.servlet.DispatcherServlet;
import ua.simpleservletframework.mvc.annotation.annotation.mapping.*;
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
import static ua.simpleservletframework.mvc.utils.RequestMethod.*;
import static ua.simpleservletframework.mvc.utils.UriUtils.formatRequestUri;

public class MappingAnnotationProcessor {
    private final Context<?> context = new Context<>();
    private final MappingUtils mappingUtils = new MappingUtils();

    private Set<Class<?>> getRequiredControllers(Set<Class<?>> controllers, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return controllers.stream()
                .filter(c -> {
                    if (c.isAnnotationPresent(Controller.class)) {
                        if (request.getMethod().equals(GET)) {
                            return Arrays.stream(c.getDeclaredMethods())
                                    .anyMatch(rm -> rm.isAnnotationPresent(GetMapping.class)
                                            &&
                                            formatRequestUri(c.getAnnotation(Controller.class).value(), rm.getAnnotation(GetMapping.class).value())
                                                    .equals(requestUri));
                        } else if (request.getMethod().equals(POST)) {
                            return Arrays.stream(c.getDeclaredMethods())
                                    .anyMatch(rm -> rm.isAnnotationPresent(PostMapping.class)
                                            &&
                                            formatRequestUri(c.getAnnotation(Controller.class).value(), rm.getAnnotation(PostMapping.class).value())
                                                    .equals(requestUri));
                        } else if (request.getMethod().equals(PUT)) {
                            return Arrays.stream(c.getDeclaredMethods())
                                    .anyMatch(rm -> rm.isAnnotationPresent(PutMapping.class)
                                            &&
                                            formatRequestUri(c.getAnnotation(Controller.class).value(), rm.getAnnotation(PutMapping.class).value())
                                                    .equals(requestUri));
                        } else if (request.getMethod().equals(DELETE)) {
                            return Arrays.stream(c.getDeclaredMethods())
                                    .anyMatch(rm -> rm.isAnnotationPresent(DeleteMapping.class)
                                            &&
                                            formatRequestUri(c.getAnnotation(Controller.class).value(), rm.getAnnotation(DeleteMapping.class).value())
                                                    .equals(requestUri));
                        } else if (request.getMethod().equals(OPTIONS)) {
                            return Arrays.stream(c.getDeclaredMethods())
                                    .anyMatch(rm -> rm.isAnnotationPresent(OptionsMapping.class)
                                            &&
                                            formatRequestUri(c.getAnnotation(Controller.class).value(), rm.getAnnotation(OptionsMapping.class).value())
                                                    .equals(requestUri));
                        } else {
                            throw new RuntimeException(UNKNOWN_REQUEST_TYPE);
                        }
                    } else if (c.isAnnotationPresent(RestController.class)) {
                        if (request.getMethod().equals(GET)) {
                            return Arrays.stream(c.getDeclaredMethods())
                                    .anyMatch(rm -> rm.isAnnotationPresent(GetMapping.class)
                                            &&
                                            formatRequestUri(c.getAnnotation(RestController.class).value(), rm.getAnnotation(GetMapping.class).value())
                                                    .equals(requestUri));
                        } else if (request.getMethod().equals(POST)) {
                            return Arrays.stream(c.getDeclaredMethods())
                                    .anyMatch(rm -> rm.isAnnotationPresent(PostMapping.class)
                                            &&
                                            formatRequestUri(c.getAnnotation(RestController.class).value(), rm.getAnnotation(PostMapping.class).value())
                                                    .equals(requestUri));
                        } else if (request.getMethod().equals(PUT)) {
                            return Arrays.stream(c.getDeclaredMethods())
                                    .anyMatch(rm -> rm.isAnnotationPresent(PutMapping.class)
                                            &&
                                            formatRequestUri(c.getAnnotation(RestController.class).value(), rm.getAnnotation(PutMapping.class).value())
                                                    .equals(requestUri));
                        } else if (request.getMethod().equals(DELETE)) {
                            return Arrays.stream(c.getDeclaredMethods())
                                    .anyMatch(rm -> rm.isAnnotationPresent(DeleteMapping.class)
                                            &&
                                            formatRequestUri(c.getAnnotation(RestController.class).value(), rm.getAnnotation(DeleteMapping.class).value())
                                                    .equals(requestUri));
                        } else if (request.getMethod().equals(OPTIONS)) {
                            return Arrays.stream(c.getDeclaredMethods())
                                    .anyMatch(rm -> rm.isAnnotationPresent(OptionsMapping.class)
                                            &&
                                            formatRequestUri(c.getAnnotation(RestController.class).value(), rm.getAnnotation(OptionsMapping.class).value())
                                                    .equals(requestUri));
                        } else {
                            throw new RuntimeException(UNKNOWN_REQUEST_TYPE);
                        }
                    } else {
                        throw new RuntimeException(MULTIPLE_CONTROLLER_TYPE_EXCEPTION);
                    }
                })
                .collect(Collectors.toSet());
    }

    private Map.Entry<? extends Class<?>, Method> getRequiredMethod(Set<Class<?>> requiredControllers, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return requiredControllers.stream()
                .map(c -> {
                    Method mapping = Arrays.stream(c.getDeclaredMethods())
                            .filter(m -> {
                                if (c.isAnnotationPresent(Controller.class)) {
                                    if (m.isAnnotationPresent(GetMapping.class)) {
                                        return m.isAnnotationPresent(GetMapping.class)
                                                &&
                                                formatRequestUri(c.getAnnotation(Controller.class).value(), m.getAnnotation(GetMapping.class).value())
                                                        .equals(requestUri);
                                    } else if (m.isAnnotationPresent(PostMapping.class)) {
                                        return m.isAnnotationPresent(PostMapping.class)
                                                &&
                                                formatRequestUri(c.getAnnotation(Controller.class).value(), m.getAnnotation(PostMapping.class).value())
                                                        .equals(requestUri);
                                    } else if (m.isAnnotationPresent(PutMapping.class)) {
                                        return m.isAnnotationPresent(PutMapping.class)
                                                &&
                                                formatRequestUri(c.getAnnotation(Controller.class).value(), m.getAnnotation(PutMapping.class).value())
                                                        .equals(requestUri);
                                    } else if (m.isAnnotationPresent(DeleteMapping.class)) {
                                        return m.isAnnotationPresent(DeleteMapping.class)
                                                &&
                                                formatRequestUri(c.getAnnotation(Controller.class).value(), m.getAnnotation(DeleteMapping.class).value())
                                                        .equals(requestUri);
                                    } else {
                                        return m.isAnnotationPresent(OptionsMapping.class)
                                                &&
                                                formatRequestUri(c.getAnnotation(Controller.class).value(), m.getAnnotation(OptionsMapping.class).value())
                                                        .equals(requestUri);
                                    }
                                } else if (c.isAnnotationPresent(RestController.class)) {
                                    if (m.isAnnotationPresent(GetMapping.class)) {
                                        return m.isAnnotationPresent(GetMapping.class)
                                                &&
                                                formatRequestUri(c.getAnnotation(RestController.class).value(), m.getAnnotation(GetMapping.class).value())
                                                        .equals(requestUri);
                                    } else if (m.isAnnotationPresent(PostMapping.class)) {
                                        return m.isAnnotationPresent(PostMapping.class)
                                                &&
                                                formatRequestUri(c.getAnnotation(RestController.class).value(), m.getAnnotation(PostMapping.class).value())
                                                        .equals(requestUri);
                                    } else if (m.isAnnotationPresent(PutMapping.class)) {
                                        return m.isAnnotationPresent(PutMapping.class)
                                                &&
                                                formatRequestUri(c.getAnnotation(RestController.class).value(), m.getAnnotation(PutMapping.class).value())
                                                        .equals(requestUri);
                                    } else if (m.isAnnotationPresent(DeleteMapping.class)) {
                                        return m.isAnnotationPresent(DeleteMapping.class)
                                                &&
                                                formatRequestUri(c.getAnnotation(RestController.class).value(), m.getAnnotation(DeleteMapping.class).value())
                                                        .equals(requestUri);
                                    } else {
                                        return m.isAnnotationPresent(OptionsMapping.class)
                                                &&
                                                formatRequestUri(c.getAnnotation(RestController.class).value(), m.getAnnotation(OptionsMapping.class).value())
                                                        .equals(requestUri);
                                    }
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
                                } else {
                                    return m.isAnnotationPresent(OptionsMapping.class);
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
            Object result = mapping.invoke(context.getBean(controller).getValue());

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
        Object result = mapping.invoke(context.getBean(controller).getValue());
        mappingUtils.mappingHandler(result, controller);
    }

    public void mappingHandler(Set<Class<?>> controllers, HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ServletException {
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
            ) {
                otherRequestTypesHandler(controller, mapping);
            }
        }
    }
}
