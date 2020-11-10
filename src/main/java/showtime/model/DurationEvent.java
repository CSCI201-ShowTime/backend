package showtime.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "duration_event")
@PrimaryKeyJoinColumn(name = "eventid")
public class DurationEvent extends Event {

    @Id
    @Column(name = "eventid", nullable = false, unique = true)
    @NotNull
    private int eventid;

    @Column(name = "remind_time")
    private Timestamp remindTime;

    public DurationEvent() {
    }

    @Override
    public String toString() {
        return "DurationEvent{" +
                "eventid=" + eventid +
                ", remind_time=" + remindTime +
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

    public Timestamp getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Timestamp remind_time) {
        this.remindTime = remind_time;
    }
}
