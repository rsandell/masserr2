package net.joinedminds.masserr.model;

/**
 * The amount of money a role starts with in his bank account.
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class StarterMoneyRule {
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
