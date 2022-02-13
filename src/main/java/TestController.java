import ua.simpleservletframework.mvc.annotation.annotation.controller.Controller;
import ua.simpleservletframework.mvc.annotation.annotation.mapping.GetMapping;
import ua.simpleservletframework.mvc.annotation.annotation.url.PathVariable;
import ua.simpleservletframework.mvc.ui.Model;

@Controller("/r")
public class TestController {
    @GetMapping("/human/{id}")
    public String get(@PathVariable String id) {
        new Model().addAttribute("id", id);
        return "test";
    }
}
