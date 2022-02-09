import ua.simpleservletframework.core.annotation.annotation.controller.RestController;
import ua.simpleservletframework.core.annotation.annotation.mapping.GetMapping;
import ua.simpleservletframework.core.context.Context;

import java.lang.reflect.InvocationTargetException;

@RestController
public class TestController {
    @GetMapping
    public String beanTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Context<TestBean> context = new Context<>();
        TestBean testBean = context.getBean("testBean");
        return testBean.getName();
    }
}
