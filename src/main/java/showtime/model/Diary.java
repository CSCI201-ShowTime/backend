package showtime.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "diary")
@PrimaryKeyJoinColumn(name = "eventid")
public class Diary extends Event {

    @Id
    @Column(name = "eventid", nullable = false, unique = true)
    @NotNull
    private int eventid;

    public Diary(@NotNull int userid, @NotNull Timestamp start, Timestamp end,
                 @NotNull String title, String description, @NotNull int visibility,
                 @NotNull int type, String location) {
        super(userid, start, end, title, description, visibility, type, location);
    }

    public Diary() {
    }

    @Override
    public String toString() {
        return "Diary{" +
                "eventid=" + eventid +
                "} " + super.toString();
    }

    @Override
    public int getEventid() {
        return eventid;
    }

    @Override
    public void setEventid(int eventid) {
        this.eventid = eventid;
    }
}
