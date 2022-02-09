import ua.simpleservletframework.core.annotation.annotation.component.Autowired;
import ua.simpleservletframework.core.annotation.annotation.controller.RestController;
import ua.simpleservletframework.core.annotation.annotation.mapping.GetMapping;
import ua.simpleservletframework.core.ui.Model;

@RestController("/test")
public class TestController {
    @Autowired
    private TestBean testBean;

    @GetMapping("/123")
    public String testGet() {
        new Model().addAttribute( "is autowired", testBean.getName());
        return (String) new Model().getAttribute("is autowired");
    }
}
