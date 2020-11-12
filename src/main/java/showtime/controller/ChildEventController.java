package showtime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import showtime.model.DurationEvent;
import showtime.repository.BudgetRepository;
import showtime.repository.DiaryRepository;
import showtime.repository.DurationEventRepository;
import showtime.repository.ReminderRepository;

import java.util.List;

@Controller
@RequestMapping("/api/event")
public class ChildEventController {

    @Autowired
    DurationEventRepository durationEventRepo;
    @Autowired
    ReminderRepository reminderRepo;
    @Autowired
    DiaryRepository diaryRepo;
    @Autowired
    BudgetRepository budgetRepo;
}
