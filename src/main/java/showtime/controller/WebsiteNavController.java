package showtime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WebsiteNavController {

    @RequestMapping("/")
    public String index() {
        return "home.html";
    }

    // any path that does not end with .html in the ROOT (static) folder
    @RequestMapping("/{path:^.*(?<!\\.html)$}")
    public String websites(@PathVariable String path) {
        return path + ".html";
    }
}