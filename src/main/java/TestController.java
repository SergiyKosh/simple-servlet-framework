import ua.simpleservletframework.core.annotation.annotation.controller.RestController;
import ua.simpleservletframework.core.annotation.annotation.mapping.GetMapping;
import ua.simpleservletframework.core.beans.impl.BeanImplementation;

import java.lang.reflect.InvocationTargetException;

@RestController
public class TestController {
    @GetMapping
    public String get() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestBean testBean = BeanImplementation.getBean("testBean");
        return testBean.name;
    }
}
