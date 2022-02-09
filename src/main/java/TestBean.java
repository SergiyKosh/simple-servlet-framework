import ua.simpleservletframework.core.annotation.annotation.component.Component;

@Component("testBean")
public class TestBean {
    private String name;

    public TestBean() {
        name = "Test Bean";
    }

    public String getName() {
        return name;
    }
}
