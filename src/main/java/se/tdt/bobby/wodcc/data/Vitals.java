package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description
 *
 * Created: 2004-mar-08 14:26:57
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class Vitals implements Serializable {
    private int mId;
    private String mName;

    /**
     * For serialization
     */
    public Vitals() {
    }

    private Vitals(int pId, String pName) {
        mId = pId;
        mName = pName;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String toString() {
        return mName;
    }

    public boolean isFinalDeath() {
        return (this == FINAL_DEATH);
    }

    public static final Vitals NORMAL = new Vitals(1, "Normal");
    public static final Vitals TORPOR = new Vitals(2, "Torpor");
    public static final Vitals FINAL_DEATH = new Vitals(3, "Final Death");
    public static final Vitals DISAPEARED = new Vitals(4, "Disapeared");
    public static final Vitals ON_LONG_JURNEY = new Vitals(5, "On Long Jurney");

    public static final Vitals[] ALL = {NORMAL, TORPOR, FINAL_DEATH, DISAPEARED, ON_LONG_JURNEY};

    public static Vitals getVital(int pId) {
        for (int i = 0; i < ALL.length; i++) {
            Vitals vitals = ALL[i];
            if(vitals.getId() == pId) {
                return vitals;
            }
        }
        return null;
    }
}
