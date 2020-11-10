package showtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import showtime.model.Diary;
import showtime.model.Event;
import showtime.repository.EventRepository;
import showtime.service.EventSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

/*    @Autowired
    EventRepository eventRepo;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            List<String> list = new ArrayList<>();
            list.add("2");
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.addAll("eventid", list);
            Optional<Event> eventO = eventRepo.findOne(new EventSpecification(map));
            System.out.println(eventO.get());
        };
    }*/
}
