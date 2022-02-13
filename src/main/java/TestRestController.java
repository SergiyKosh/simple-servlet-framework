import ua.simpleservletframework.mvc.annotation.annotation.controller.RestController;
import ua.simpleservletframework.mvc.annotation.annotation.mapping.GetMapping;
import ua.simpleservletframework.mvc.annotation.annotation.mapping.PostMapping;
import ua.simpleservletframework.mvc.annotation.annotation.url.PathVariable;

@RestController("/u")
public class TestRestController {
    @GetMapping("/{id}")
    public String getRM(@PathVariable String id) {
        return id;
    }

    @GetMapping
    public String hello() {
        return "hello";
    }

    @PostMapping
    public String post() {
        return "redirect:/u";
    }
}
