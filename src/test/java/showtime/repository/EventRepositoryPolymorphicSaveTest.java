package showtime.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import showtime.model.Diary;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EventRepositoryPolymorphicSaveTest {

    @Autowired
    private EventRepository eventRepo;
    @Autowired
    private DurationEventRepository durationEventRepo;
    @Autowired
    private ReminderRepository reminderRepo;
    @Autowired
    private DiaryRepository diaryRepo;
    @Autowired
    private BudgetRepository budgetRepo;

    @Test
    void givenDiary_whenSaveUsingEventRepo_thenSaveToOnlyEventAndDiaryTable() {
        Diary diary = new Diary(1,
                LocalDateTime.parse("2020-11-11T03:50:59"),
                null,
                "testdiary1",
                "testdescription1",
                0, 3,
                null
        );
        long eventCount = eventRepo.count();
        long durationCount = durationEventRepo.count();
        long reminderCount = reminderRepo.count();
        long diaryCount = diaryRepo.count();
        long budgetCount = budgetRepo.count();
        Diary newDiary = eventRepo.save(diary);
        assertEquals(eventRepo.count(), eventCount+1);
        assertEquals(durationEventRepo.count(), durationCount);
        assertEquals(reminderRepo.count(), reminderCount);
        assertEquals(diaryRepo.count(), diaryCount+1);
        assertEquals(budgetRepo.count(), budgetCount);
    }
}
