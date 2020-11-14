package showtime.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "budget")
@PrimaryKeyJoinColumn(name = "eventid")
@DiscriminatorValue("4")
public class Budget extends Event {

    @Column(name = "amount", nullable = false, columnDefinition="Decimal(11,3)")
    @NotNull
    private double amount;

    @Column(name = "category")
    private String category;

    // may be NULL
    // TODO: change primitives to Boxed
    @Column(name = "ebud_transaction_userid")
    private Integer ebudTransactionUserid;

    public Budget(@NotNull int userid, @NotNull LocalDateTime start, LocalDateTime end,
                  @NotNull String title, String description,
                  @NotNull int visibility, @NotNull int type, String location,
                  @NotNull double amount, String category, Integer ebudTransactionUserid) {
        super(userid, start, end, title, description, visibility, type, location);
        this.amount = amount;
        this.category = category;
        this.ebudTransactionUserid = ebudTransactionUserid;
    }

    public Budget() {
    }

    @Override
    public String toString() {
        return "Budget{" +
                "amount=" + amount +
                ", category='" + category + '\'' +
                ", ebudTransactionUserid=" + ebudTransactionUserid +
                "} " + super.toString();
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

    public Integer getEbudTransactionUserid() {
        return ebudTransactionUserid;
    }

    public void setEbudTransactionUserid(Integer userid) {
        this.ebudTransactionUserid = userid;
    }
}
