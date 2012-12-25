package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-10 00:14:20
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class Ability implements Serializable, Cloneable  {

	private int mId;
	private String mName;
	private char mType;
	private String mSpecialisation;
	private int mDots;
	private int mBaseMonthlyIncome;

	public Ability() {
	}

	public Ability(int pId, String pName, char pType) {
		mId = pId;
		mName = pName;
		mType = pType;
		mSpecialisation = null;
		mDots = 0;
	}

	public Ability(int pId, String pName, char pType, int pBaseMonthlyIncome) {
		mId = pId;
		mName = pName;
		mType = pType;
		mBaseMonthlyIncome = pBaseMonthlyIncome;
	}

	public Ability(int pId, String pName, char pType, String pSpecialisation, int pDots) {
		mId = pId;
		mName = pName;
		mType = pType;
		mSpecialisation = pSpecialisation;
		mDots = pDots;
	}

	public Ability(int pId, String pName, char pType, String pSpecialisation, int pDots, int pBaseMonthlyIncome) {
		mId = pId;
		mName = pName;
		mType = pType;
		mSpecialisation = pSpecialisation;
		mDots = pDots;
		mBaseMonthlyIncome = pBaseMonthlyIncome;
	}

	public Ability(int pId, String pName, char pType, String pSpecialisation) {
		mId = pId;
		mName = pName;
		mType = pType;
		mSpecialisation = pSpecialisation;
		mDots = 0;
	}

    public Object clone() {
        return new Ability(mId, mName, mType, mSpecialisation, mDots, mBaseMonthlyIncome);
    }

	public int getBaseMonthlyIncome() {
		return mBaseMonthlyIncome;
	}

	public void setBaseMonthlyIncome(int pBaseMonthlyIncome) {
		mBaseMonthlyIncome = pBaseMonthlyIncome;
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

	public char getType() {
		return mType;
	}

	public void setType(char pType) {
		mType = pType;
	}

	public String toString() {
		return mName;
	}

	public String getSpecialisation() {
		return mSpecialisation;
	}

	public void setSpecialisation(String pSpecialisation) {
		mSpecialisation = pSpecialisation;
	}

	public int getDots() {
		return mDots;
	}

	public void setDots(int pDots) {
		mDots = pDots;
	}
}
