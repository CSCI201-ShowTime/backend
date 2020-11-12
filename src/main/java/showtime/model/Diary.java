package showtime.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "diary")
@PrimaryKeyJoinColumn(name = "eventid")
public class Diary extends Event {

    public Diary(@NotNull int userid, @NotNull LocalDateTime start, LocalDateTime end,
                 @NotNull String title, String description,
                 @NotNull int visibility, @NotNull int type, String location) {
        super(userid, start, end, title, description, visibility, type, location);
    }

    public Diary() {
    }

    @Override
    public String toString() {
        return "Diary{} " + super.toString();
    }
}
