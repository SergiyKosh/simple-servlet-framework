import ua.simpleservletframework.mvc.annotation.annotation.controller.Controller;
import ua.simpleservletframework.mvc.annotation.annotation.controller.RestController;
import ua.simpleservletframework.mvc.annotation.annotation.mapping.GetMapping;

@RestController
public class TestController {
    @GetMapping("/i")
    public String get() {
        return "i";
    }
}
