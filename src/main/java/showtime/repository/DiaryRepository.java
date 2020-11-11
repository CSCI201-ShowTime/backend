package showtime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import showtime.model.Diary;
import showtime.model.Event;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {
}
