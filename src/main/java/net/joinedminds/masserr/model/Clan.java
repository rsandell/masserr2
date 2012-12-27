package net.joinedminds.masserr.model;

import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-10 01:52:34
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class Clan implements Serializable, Cloneable  {
	private int mId;
	private String mName;
	private int mBaseIncome;
    private String mWeaknesses = "";

	public Clan() {
	}

	public Clan(int pId, String pName) {
		mId = pId;
		mName = pName;
	}

	public Clan(int pId, String pName, int pBaseIncome) {
		mId = pId;
		mName = pName;
		mBaseIncome = pBaseIncome;
	}

    public Clan(int pId, String pName, int pBaseIncome, String pWeaknesses) {
        mId = pId;
        mName = pName;
        mBaseIncome = pBaseIncome;
        mWeaknesses = pWeaknesses;
    }

    public Object clone() {
        return new Clan(mId, mName, mBaseIncome, mWeaknesses);
    }

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public void setId(int pId) {
		mId = pId;
	}

	public void setName(String pName) {
		mName = pName;
	}

	public String toString() {
		return mName;
	}

	public int getBaseIncome() {
		return mBaseIncome;
	}

	public void setBaseIncome(int pBaseIncome) {
		mBaseIncome = pBaseIncome;
	}

    public String getWeaknesses() {
        return mWeaknesses;
    }

    public void setWeaknesses(String pWeaknesses) {
        mWeaknesses = pWeaknesses;
    }
}
