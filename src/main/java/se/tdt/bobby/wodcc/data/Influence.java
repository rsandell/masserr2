package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description
 *
 *
 * Created: 2004-feb-03 00:43:53
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class Influence implements Serializable, Cloneable {

    private int mId;
    private String mName;
    private int mDots;
    private String mNotes;

    public Influence() {
    }

    public Influence(int pId, String pName, int pDots) {
        mId = pId;
        mName = pName;
        mDots = pDots;
    }

    public Influence(int pId, String pName, int pDots, String pNotes) {
        mId = pId;
        mName = pName;
        mDots = pDots;
        mNotes = pNotes;
    }

    public Influence(int pId, String pName) {
        mId = pId;
        mName = pName;
        mDots = 0;
    }

    public Object clone()  {
        return new Influence(mId, mName, mDots, mNotes);
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String pNotes) {
        mNotes = pNotes;
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

    public int getDots() {
        return mDots;
    }

    public void setDots(int pDots) {
        mDots = pDots;
    }

    public String toString() {
        String str = mName;
        if (mDots > 0) {
            str += " " + mDots;
        }
        if (mNotes != null && mNotes.length() > 0) {
            str += " (" + mNotes + ")";
        }
        return str;
    }
}
