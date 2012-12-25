package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-feb-02 22:17:26
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class Profession implements Serializable, Cloneable {
	public static int MASK_INCOME_FACTOR = 10;
	private int mId;
	private String mName;
	private int mMonthlyIncome;
	private boolean mMortal;
	private boolean mMask;

	public Profession() {
	}

	public Profession(int pId, String pName, int pMonthlyIncome, boolean pMortal, boolean pMask) {
		mId = pId;
		mName = pName;
		mMonthlyIncome = pMonthlyIncome;
		mMortal = pMortal;
		mMask = pMask;
	}

	public Profession(int pId, String pName, int pMonthlyIncome, boolean pMortal) {
		mId = pId;
		mName = pName;
		mMonthlyIncome = pMonthlyIncome;
		mMortal = pMortal;
		mMask = false;
	}

    public Object clone() {
        return new Profession(mId, mName, mMonthlyIncome, mMortal, mMask);
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

	public int getMonthlyIncome() {
		return mMonthlyIncome;
	}

	public void setMonthlyIncome(int pMonthlyIncome) {
		mMonthlyIncome = pMonthlyIncome;
	}

	public boolean isMortal() {
		return mMortal;
	}

	public void setMortal(boolean pMortal) {
		mMortal = pMortal;
	}

	public boolean isMask() {
		return mMask;
	}

	public void setMask(boolean pMask) {
		mMask = pMask;
	}

	public String toString() {
		return mName;
	}
}
