package ua.simpleservletframework.mvc.annotation.processor.url;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import ua.simpleservletframework.mvc.annotation.annotation.mapping.GetMapping;
import ua.simpleservletframework.mvc.annotation.annotation.url.PathVariable;

import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class PathVariableAnnotationProcessor {
    private Set<Parameter> getAllPathVariables() {
        return new Reflections(ClasspathHelper.forClassLoader())
                .getMethodsAnnotatedWith(GetMapping.class).stream()
                .map(Executable::getParameters)
                .filter(parameters -> parameters.length != 0)
                .flatMap(Arrays::stream)
                .filter(parameter -> parameter.isAnnotationPresent(PathVariable.class))
                .collect(Collectors.toSet());
    }

    private Map<Object, String> getPathVariablesWithValues(Set<Parameter> pv) {
        return pv.stream()
                .map(parameter -> {
                    Map<Object, String> pvwv = new HashMap<>();
                    pvwv.put(parameter.getName(), parameter.getAnnotation(PathVariable.class).value());
                    return pvwv;
                })
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
