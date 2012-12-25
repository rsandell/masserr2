package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-22 22:12:29
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class RitualType implements Serializable, Cloneable {
    private int mId;
    private String mName;

    public RitualType(int pId, String pName) {
        mId = pId;
        mName = pName;
    }

    public RitualType(String pName) {
        mName = pName;
        mId = -1;
    }

    public Object clone() {
        return new RitualType(mId, mName);
    }

    /**
     * For serialization
     */
    public RitualType() {
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

    public String toString() {
        return mName;
    }
}
