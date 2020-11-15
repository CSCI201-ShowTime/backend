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
import showtime.repository.BudgetRepository;
import showtime.repository.EventBaseRepository;
import showtime.repository.EventRepository;
import showtime.service.BudgetSpecBuilder;
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
    @Autowired
    BudgetRepository budgetRepo;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            List<EventBaseRepository> ebrl = new ArrayList<>();
            ebrl.add(eventRepo);
            ebrl.add(budgetRepo);

            List<Event> e = ebrl.get(1).findAll(new BudgetSpecBuilder().byAmount(List.of(5.0)).build());
            System.out.println(e);
        };
    }
}
