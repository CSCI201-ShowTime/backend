package showtime.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import showtime.model.Budget;
import showtime.model.Diary;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EventRepositoryPolymorphicSaveTest {

    @Autowired
    private EventRepository eventRepo;
    @Autowired
    private DiaryRepository diaryRepo;
    @Autowired
    private BudgetRepository budgetRepo;

    @BeforeAll
    static void initAll() {

    }

    @Test
    void givenDiary_whenSaveUsingEventRepo_thenSaveToOnlyEventAndDiaryTable() {
        Diary diary = new Diary(1,
                new Timestamp(2020, 11, 11, 22, 0,0,0),
                null,
                "testdiary1",
                "testdescription1",
                0, 1,
                null
        );
        long eventCount = eventRepo.count();
        long diaryCount = diaryRepo.count();
        long budgetCount = budgetRepo.count();
        Diary newDiary = eventRepo.save(diary);
        assertEquals(eventRepo.count(), eventCount+1);
        assertEquals(diaryRepo.count(), diaryCount+1);
        assertEquals(budgetRepo.count(), budgetCount);
    }
}
