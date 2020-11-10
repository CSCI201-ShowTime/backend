package showtime.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "reminder")
@PrimaryKeyJoinColumn(name = "eventid")
public class Reminder extends Event {

    @Id
    @Column(name = "eventid", nullable = false, unique = true)
    @NotNull
    private int eventid;

    @Column(name = "remind_time")
    private Timestamp remindTime;

    @Column(name = "priority")
    private int priority;

    public Reminder() {
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "eventid=" + eventid +
                ", remind_time=" + remindTime +
                ", priority=" + priority +
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
