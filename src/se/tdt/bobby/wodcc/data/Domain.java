package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description
 *
 * Created: 2004-mar-08 17:06:13
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class Domain implements Serializable, Cloneable {

    private int mId;
    private String mName;
    private String mHistory;

    public Domain() {
    }

    public Domain(int pId, String pName, String pHistory) {
        mId = pId;
        mName = pName;
        mHistory = pHistory;
    }

    public Domain(int pId, String pName) {
        mId = pId;
        mName = pName;
    }

    public Object clone() {
        return new Domain(mId, mName, mHistory);
    }

    public boolean equals(Domain pDomain) {
        return pDomain.getId() == mId;
    }

    public boolean equals(Object obj) {
        try {
            return this.equals((Domain)obj);
        }
        catch (Exception e) {
            return false;
        }
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

    public String getHistory() {
        return mHistory;
    }

    public void setHistory(String pHistory) {
        mHistory = pHistory;
    }

    public String toString() {
        return mName;
    }
}
