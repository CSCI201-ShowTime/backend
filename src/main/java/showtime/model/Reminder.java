package showtime.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "reminder")
@PrimaryKeyJoinColumn(name = "eventid")
@DiscriminatorValue("2")
public class Reminder extends Event {

    @Column(name = "remind_time")
    private LocalDateTime remindTime;

    @Column(name = "priority")
    private int priority;

    public Reminder() {
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "remindTime=" + remindTime +
                ", priority=" + priority +
                "} " + super.toString();
    }

    public LocalDateTime getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(LocalDateTime remind_time) {
        this.remindTime = remind_time;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
