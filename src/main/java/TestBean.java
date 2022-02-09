import ua.simpleservletframework.core.annotation.annotation.component.Bean;

@Bean("testBean")
public class TestBean {
    public String name;

    public TestBean() {
        name = "Test Bean";
    }
}
