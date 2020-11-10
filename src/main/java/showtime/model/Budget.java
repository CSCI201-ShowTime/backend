package showtime.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "budget")
@PrimaryKeyJoinColumn(name = "eventid")
public class Budget extends Event {

    @Id
    @Column(name = "eventid", nullable = false, unique = true)
    @NotNull
    private int eventid;

    @Column(name = "amount", nullable = false, columnDefinition="Decimal(11,3)")
    @NotNull
    private double amount;

    @Column(name = "category")
    private String category;

    @Column(name = "userid")
    private int userid;

    public Budget() {
    }

    @Override
    public String toString() {
        return "Budget{" +
                "eventid=" + eventid +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", userid=" + userid +
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int getUserid() {
        return userid;
    }

    @Override
    public void setUserid(int userid) {
        this.userid = userid;
    }
}
