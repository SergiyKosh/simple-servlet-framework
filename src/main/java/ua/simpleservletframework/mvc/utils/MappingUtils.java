package ua.simpleservletframework.mvc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import ua.simpleservletframework.core.context.Context;
import ua.simpleservletframework.mvc.annotation.annotation.controller.Controller;
import ua.simpleservletframework.mvc.annotation.annotation.controller.RestController;
import ua.simpleservletframework.mvc.annotation.annotation.mapping.*;
import ua.simpleservletframework.mvc.servlet.DispatcherServlet;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.Stream;

import static ua.simpleservletframework.core.util.Constants.UNKNOWN_REQUEST_TYPE;
import static ua.simpleservletframework.mvc.servlet.DispatcherServlet.request;
import static ua.simpleservletframework.mvc.servlet.DispatcherServlet.response;
import static ua.simpleservletframework.mvc.utils.UriUtils.*;


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

    public static Map<String, String> getPVValues(String uri, String cUri) {
        String[] cUriArr = cUri.split("/");
        String[] mUriArr;
        if (cUriArr.length > 0) {
            mUriArr = uri.substring(1).split("/");
        } else {
            mUriArr = uri.split("/");
        }

        String[] uriArr = new String[]{};
        String[] finalUriArr = uriArr;
        uriArr = Stream.concat(Arrays.stream(uriArr), Arrays.stream(cUriArr)).toArray(size -> (String[])
                Array.newInstance(finalUriArr.getClass().getComponentType(), size));
        String[] finalUriArr1 = uriArr;
        uriArr = Stream.concat(Arrays.stream(uriArr), Arrays.stream(mUriArr)).toArray(size -> (String[])
                Array.newInstance(finalUriArr1.getClass().getComponentType(), size));
        String[] reqUriArr = DispatcherServlet.request.getRequestURI().split("/");

        Map<String, String> pathVariables = new HashMap<>();

        for (int i = 0; i < uriArr.length; i++) {
            if (!reqUriArr[i].equals(uriArr[i])) {
                pathVariables.put(uriArr[i].substring(1), reqUriArr[i]);
            }
        }

        return pathVariables;
    }

    public static Object getMappingMethodResult(
            Context<?> context,
            Class<?> controller,
            Method mapping
    ) throws InvocationTargetException, IllegalAccessException {
        Object result;
        if (mapping.getParameters().length != 0) {
            String cUri;
            if (controller.isAnnotationPresent(Controller.class)) {
                cUri = controller.getAnnotation(Controller.class).value();
            } else {
                cUri = controller.getAnnotation(RestController.class).value();
            }
            String[] pathVars = getPVValues(
                    getUriFromMapping(mapping),
                    cUri
            )
                    .values()
                    .toArray(new String[0]);
            result = mapping.invoke(context.getBean(controller).getValue(), pathVars);
        } else {
            result = mapping.invoke(context.getBean(controller).getValue());
        }

        return result;
    }

    public static boolean getRequiredControllers(
            Class<?> c,
            String requestUri,
            String controllerUri
    ) {
        AtomicReference<String> rUri = new AtomicReference<>();
        AtomicReference<String> reqUri = new AtomicReference<>();
        AtomicReference<AtomicReferenceArray<String>> injected = new AtomicReference<>(new AtomicReferenceArray<>(new String[0]));
        AtomicReference<String> collected = new AtomicReference<>();

        return Arrays.stream(c.getDeclaredMethods())
                .anyMatch(rm -> {
                    rUri.set(getUriFromMapping(rm));
                    reqUri.set(formatRequestUri(controllerUri, rUri.get()));
                    injected.set(new AtomicReferenceArray<>(injectPathVariableIfExists(requestUri, reqUri.get())));
                    collected.set(collectUri(injected));

                    return collected.get().equals(requestUri);
                });
    }

    public static boolean getRequiredMethod(
            Method m,
            String requestUri,
            String controllerUri
    ) {
        AtomicReference<String> rUri = new AtomicReference<>();
        AtomicReference<String> reqUri = new AtomicReference<>();
        AtomicReference<AtomicReferenceArray<String>> injected = new AtomicReference<>(new AtomicReferenceArray<>(new String[0]));
        AtomicReference<String> collected = new AtomicReference<>();

        rUri.set(getUriFromMapping(m));
        reqUri.set(formatRequestUri(controllerUri, rUri.get()));
        injected.set(new AtomicReferenceArray<>(injectPathVariableIfExists(requestUri, reqUri.get())));
        collected.set(collectUri(injected));

        return collected.get().equals(requestUri);
    }

    private static String getUriFromMapping(Method method) {
        if (request.getMethod().equals(RequestMethod.GET)) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                return method.getAnnotation(GetMapping.class).value();
            }
        } else if (request.getMethod().equals(RequestMethod.POST)) {
            if (method.isAnnotationPresent(PostMapping.class)) {
                return method.getAnnotation(PostMapping.class).value();
            }
        } else if (request.getMethod().equals(RequestMethod.PUT)) {
            if (method.isAnnotationPresent(PutMapping.class)) {
                return method.getAnnotation(PutMapping.class).value();
            }
        } else if (request.getMethod().equals(RequestMethod.DELETE)) {
            if (method.isAnnotationPresent(DeleteMapping.class)) {
                return method.getAnnotation(DeleteMapping.class).value();
            }
        } else if (request.getMethod().equals(RequestMethod.OPTIONS)) {
            if (method.isAnnotationPresent(OptionsMapping.class)) {
                return method.getAnnotation(OptionsMapping.class).value();
            }
        } else if (request.getMethod().equals(RequestMethod.PATCH)) {
            if (method.isAnnotationPresent(PatchMapping.class)) {
                return method.getAnnotation(PatchMapping.class).value();
            }
        } else {
            throw new RuntimeException(UNKNOWN_REQUEST_TYPE);
        }

        return "";
    }
}

