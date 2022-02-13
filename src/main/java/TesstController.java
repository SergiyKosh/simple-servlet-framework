import ua.simpleservletframework.mvc.annotation.annotation.controller.Controller;
import ua.simpleservletframework.mvc.annotation.annotation.mapping.GetMapping;
import ua.simpleservletframework.mvc.annotation.annotation.url.PathVariable;
import ua.simpleservletframework.mvc.ui.Model;

@Controller
public class TesstController {
    @GetMapping("/{id}")
    public String get1(@PathVariable String id) {
        System.out.println(String.format("%s : %s", "id", id));
        new Model().addAttribute("id", id);
        return "tesst";
    }
}
