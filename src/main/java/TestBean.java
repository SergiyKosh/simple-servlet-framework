import ua.simpleservletframework.core.annotation.annotation.component.Bean;

@Bean("testBean")
public class TestBean {
    private final String name;

    public TestBean() {
        name = "Test Bean";
    }

    public String getName() {
        return name;
    }
}
