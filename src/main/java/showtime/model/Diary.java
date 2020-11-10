package showtime.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "diary")
@PrimaryKeyJoinColumn(name = "eventid")
public class Diary extends Event {

    @Id
    @Column(name = "eventid", nullable = false, unique = true)
    @NotNull
    private int eventid;

    public Diary() {
    }

    @Override
    public String toString() {
        return "Basic{" +
                "eventid=" + eventid +
                '}';
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
