package net.joinedminds.masserr.model;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Index;
import com.github.jmkgreen.morphia.annotations.Indexes;
import org.bson.types.ObjectId;

/**
 * The amount of money a role starts with in his bank account.
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Entity
@Indexes(@Index("minAge, maxAge"))
public class StarterMoneyRule {
    @Id
    private ObjectId objectId;
    private int minAge;
    private int maxAge;
    private int baseMoney;

    public StarterMoneyRule(int minAge, int maxAge, int baseMoney) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.baseMoney = baseMoney;
    }

    public StarterMoneyRule() {
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getBaseMoney() {
        return baseMoney;
    }

    public void setBaseMoney(int baseMoney) {
        this.baseMoney = baseMoney;
    }
}
