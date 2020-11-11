package showtime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import showtime.model.Budget;
import showtime.model.Event;

public interface BudgetRepository extends JpaRepository<Budget, Integer>  {
}
