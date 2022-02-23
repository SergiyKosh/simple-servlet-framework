package ua.simpleservletframework.core.cookie;

import jakarta.servlet.http.Cookie;
import ua.simpleservletframework.core.annotation.Component;
import ua.simpleservletframework.mvc.servlet.DispatcherServlet;

@Component("cookies")
public class Cookies {
    public void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("http://localhost:8080/*");
        DispatcherServlet.response.addCookie(cookie);
    }
}
