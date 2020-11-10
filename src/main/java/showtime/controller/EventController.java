package showtime.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class EventController {

    @GetMapping("/event/rawevent")
    public ResponseEntity<?> getRawEventByAllSpecs(@RequestParam MultiValueMap<String, String> params) {
        System.out.println(params.size());
        System.out.println(params.get("type"));
        System.out.println(params.get("type"));
        System.out.println(params.get("type").getClass().getSimpleName());
        System.out.println(params.get("abc"));
        System.out.println(params.get("abc").getClass().getSimpleName());
        System.out.println(params.get("qwe"));
        System.out.println(params.getFirst("qwe") == null);
        System.out.println(params);
        ArrayList<String> list = (ArrayList<String>) params.get("type");


        return ResponseEntity.ok("ok");
    }
}
