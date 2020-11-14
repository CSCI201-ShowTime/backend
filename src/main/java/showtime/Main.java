package showtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import showtime.model.Diary;
import showtime.model.Event;
import showtime.repository.EventRepository;
import showtime.service.EventSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    EventRepository eventRepo;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

/*            Specification<Event> a = (Specification<Event>) (root, query, criteriaBuilder) -> {
                return criteriaBuilder.between(root.get("userid"), 1, 1);
            };
            List<Event> e = eventRepo.findAll(a);
            System.out.println(e);*/
/*
            List<String> list = new ArrayList<>();
            list.add("2");
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.addAll("eventid", list);
            Optional<Event> eventO = eventRepo.findOne(new EventSpecification(map));
            System.out.println(eventO.get());*/
        };
    }
}
