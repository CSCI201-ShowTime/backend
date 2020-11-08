package showtime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "home.html";
    }

    @RequestMapping("{path:^.*(?<!\\.html)$}")
    public String webpage(@PathVariable String path) {
        return path + ".html";
    }
}