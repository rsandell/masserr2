package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description.
 * <p/>
 * Created: 2004-maj-14 22:29:57
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class FightOrFlight implements Serializable, Cloneable {
    private int mId;
    private String mName;
    private String mDescription;

    public FightOrFlight(int pId, String pName, String pDescription) {
        mId = pId;
        mName = pName;
        mDescription = pDescription;
    }

    public FightOrFlight(String pName, String pDescription) {
        mName = pName;
        mDescription = pDescription;
        mId = -1;
    }

    public boolean equals(Object obj) {
        return equals((FightOrFlight)obj);
    }

    public boolean equals(FightOrFlight pFightOrFlight) {
        if (pFightOrFlight == null) {
            return false;
        }
        else {
            return mId == pFightOrFlight.getId();
        }
    }

    /**
     * For Serialization
     */
    public FightOrFlight() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int pId) {
        mId = pId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String pName) {
        mName = pName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String pDescription) {
        mDescription = pDescription;
    }

    public String toString() {
        return mName;
    }

    public Object clone() {
        return new FightOrFlight(mId, mName, mDescription);
    }
}
