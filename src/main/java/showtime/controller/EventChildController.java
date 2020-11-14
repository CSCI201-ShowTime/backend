/*
package showtime.controller;

import com.fasterxml.jackson.databind.deser.BuilderBasedDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import showtime.model.Budget;
import showtime.model.DurationEvent;
import showtime.model.Event;
import showtime.model.Reminder;
import showtime.repository.BudgetRepository;
import showtime.repository.DiaryRepository;
import showtime.repository.DurationEventRepository;
import showtime.repository.EventBaseRepository;
import showtime.repository.ReminderRepository;
import showtime.service.DurationEventSpecBuilderService;
import showtime.service.EventSpecBuilderService;
import showtime.service.ReminderSpecBuilderService;
import showtime.util.BoundedCastMap;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/event")
public class EventChildController {

*/
/*    @Autowired
    private DurationEventRepository durationEventRepo;
    @Autowired
    private ReminderRepository reminderRepo;
    @Autowired
    private DiaryRepository diaryRepo;
    @Autowired
    private BudgetRepository budgetRepo;*//*


    BoundedCastMap<EventBaseRepository<? extends Event>> repos;

    @Autowired
    public EventChildController(DurationEventRepository durationEventRepo,
                                ReminderRepository reminderRepo,
                                DiaryRepository diaryRepo,
                                BudgetRepository budgetRepo) {
        repos.put(DurationEventRepository.class, durationEventRepo);
        repos.put(ReminderRepository.class, reminderRepo);
        repos.put(DiaryRepository.class, diaryRepo);
        repos.put(BudgetRepository.class, budgetRepo);
    }

    Logger logger = LoggerFactory.getLogger(EventChildController.class);

    @GetMapping("/durationevent")
    public ResponseEntity<List<? extends Event>> getDEByCriteria(
            HttpServletRequest request,
            @RequestParam MultiValueMap<String, String> params) {

        logger.debug(request.getRequestURI());

        List<? extends Event> budlist = repos.get(BudgetRepository.class).findAll(
                new EventSpecBuilderService<Budget>().fromMultiValueMap(params).build());





        Specification<DurationEvent> specDurationEvent = new DurationEventSpecBuilderService()
                .fromMultiValueMap(params)
                .build();
        Specification<Reminder> specReminder = new ReminderSpecBuilderService()
                .fromMultiValueMap(params)
                .build();

        List<Specification<? extends Event>> specList = List.of(specDurationEvent, specReminder);

        List<DurationEvent> durationEventList =
                ((EventBaseRepository<DurationEvent>)repos.get(0)).findAll((Specification<DurationEvent>)specList.get(0));

        List<DurationEvent> de2 = this.<DurationEvent>mapping(repos.get(1), specList.get(1));


        return new ResponseEntity<>(durationEventList, HttpStatus.OK);
    }

    public <T extends Event> List<T> mapping(EventBaseRepository<T> repo, Specification<T> spec) {
        return ((EventBaseRepository<T>)repo).findAll((Specification<T>)spec);
    }

    @GetMapping("/reminder")
    public ResponseEntity<List<? extends Event>> getRByCriteria(
            HttpServletRequest request,
            @RequestParam MultiValueMap<String, String> params) {

        logger.debug(request.getRequestURI());

        List<? extends Event> budlist = repos.get(BudgetRepository.class).findAll(
                new EventSpecBuilderService<Budget>().fromMultiValueMap(params).build());





        Specification<DurationEvent> specDurationEvent = new DurationEventSpecBuilderService()
                .fromMultiValueMap(params)
                .build();
        Specification<Reminder> specReminder = new ReminderSpecBuilderService()
                .fromMultiValueMap(params)
                .build();
        List<Specification<? extends Event>> specList = List.of(specDurationEvent, specReminder);

        List<DurationEvent> durationEventList =
                ((EventBaseRepository<DurationEvent>)repos.get(0)).findAll((Specification<DurationEvent>)specList.get(0));

        List<DurationEvent> de2 = this.<DurationEvent>mapping(repos.get(1), specList.get(1));


        return new ResponseEntity<>(durationEventList, HttpStatus.OK);
    }

    @GetMapping("diary")
    public ResponseEntity<List<? extends Event>> getDByCriteria(
            HttpServletRequest request,
            @RequestParam MultiValueMap<String, String> params) {

        logger.debug(request.getRequestURI());

        List<? extends Event> budlist = repos.get(BudgetRepository.class).findAll(
                new EventSpecBuilderService<Budget>().fromMultiValueMap(params).build());





        Specification<DurationEvent> specDurationEvent = new DurationEventSpecBuilderService()
                .fromMultiValueMap(params)
                .build();
        Specification<Reminder> specReminder = new ReminderSpecBuilderService()
                .fromMultiValueMap(params)
                .build();
        List<Specification<? extends Event>> specList = List.of(specDurationEvent, specReminder);

        List<DurationEvent> durationEventList =
                ((EventBaseRepository<DurationEvent>)repos.get(0)).findAll((Specification<DurationEvent>)specList.get(0));

        List<DurationEvent> de2 = this.<DurationEvent>mapping(repos.get(1), specList.get(1));


        return new ResponseEntity<>(durationEventList, HttpStatus.OK);
    }

    public <T extends Event> List<T> mapping(EventBaseRepository<T> repo, Specification<T> spec) {
        return ((EventBaseRepository<T>)repo).findAll((Specification<T>)spec);
    }

    @GetMapping("budget")
    public ResponseEntity<List<? extends Event>> geBByCriteria(
            HttpServletRequest request,
            @RequestParam MultiValueMap<String, String> params) {

        logger.debug(request.getRequestURI());

        List<? extends Event> budlist = repos.get(BudgetRepository.class).findAll(
                new EventSpecBuilderService<Budget>().fromMultiValueMap(params).build());

        Specification<DurationEvent> specDurationEvent = new DurationEventSpecBuilderService()
                .fromMultiValueMap(params)
                .build();
        Specification<Reminder> specReminder = new ReminderSpecBuilderService()
                .fromMultiValueMap(params)
                .build();
        List<Specification<? extends Event>> specList = List.of(specDurationEvent, specReminder);

        List<DurationEvent> durationEventList =
                ((EventBaseRepository<DurationEvent>)repos.get(0)).findAll((Specification<DurationEvent>)specList.get(0));

        List<DurationEvent> de2 = this.<DurationEvent>mapping(repos.get(1), specList.get(1));


        return new ResponseEntity<>(durationEventList, HttpStatus.OK);
    }

    public <T extends Event> List<T> mapping(EventBaseRepository<T> repo, Specification<T> spec) {
        return ((EventBaseRepository<T>)repo).findAll((Specification<T>)spec);
    }

}
*/
