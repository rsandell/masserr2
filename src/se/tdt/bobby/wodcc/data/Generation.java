package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-10 01:27:51
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class Generation implements Serializable , Cloneable {
	private int mGeneration;
	private int mBloodPool;
	private int mSpendBlood;
    private int mAbilitiesMax;
    private int mDisciplinesMax;
    private int mTraitsMax;
    private int mWillpowerStart;
    private int mWillpowerMax;
    private int mHumanBlood;

    public Generation() {
	}

	public Generation(int pGeneration, int pBloodPool, int pSpendBlood, int pAbilitiesMax, int pDisciplinesMax, int pTraitsMax, int pWillpowerStart, int pWillpowerMax, int pHumanBlood) {
		mGeneration = pGeneration;
		mBloodPool = pBloodPool;
		mSpendBlood = pSpendBlood;
		mAbilitiesMax = pAbilitiesMax;
		mDisciplinesMax = pDisciplinesMax;
		mTraitsMax = pTraitsMax;
		mWillpowerStart = pWillpowerStart;
		mWillpowerMax = pWillpowerMax;
        mHumanBlood = pHumanBlood;
    }

    public Object clone() {
        return new Generation(mGeneration, mBloodPool, mSpendBlood, mAbilitiesMax, mDisciplinesMax, mTraitsMax, mWillpowerStart, mWillpowerMax, mHumanBlood);
    }

	public int getGeneration() {
		return mGeneration;
	}

	public int getBloodPool() {
		return mBloodPool;
	}

	public int getSpendBlood() {
		return mSpendBlood;
	}

	public int getAbilitiesMax() {
		return mAbilitiesMax;
	}

	public int getDisciplinesMax() {
		return mDisciplinesMax;
	}

	public int getTraitsMax() {
		return mTraitsMax;
	}

	public int getWillpowerStart() {
		return mWillpowerStart;
	}

	public int getWillpowerMax() {
		return mWillpowerMax;
	}


	public void setGeneration(int pGeneration) {
		mGeneration = pGeneration;
	}

	public void setBloodPool(int pBloodPool) {
		mBloodPool = pBloodPool;
	}

	public void setSpendBlood(int pSpendBlood) {
		mSpendBlood = pSpendBlood;
	}

	public void setAbilitiesMax(int pAbilitiesMax) {
		mAbilitiesMax = pAbilitiesMax;
	}

	public void setDisciplinesMax(int pDisciplinesMax) {
		mDisciplinesMax = pDisciplinesMax;
	}

	public void setTraitsMax(int pTraitsMax) {
		mTraitsMax = pTraitsMax;
	}

	public void setWillpowerStart(int pWillpowerStart) {
		mWillpowerStart = pWillpowerStart;
	}

	public void setWillpowerMax(int pWillpowerMax) {
		mWillpowerMax = pWillpowerMax;
	}

    public int getHumanBlood() {
        return mHumanBlood;
    }

    public void setHumanBlood(int pHumanBlood) {
        mHumanBlood = pHumanBlood;
    }

	public String toString() {
		return String.valueOf(mGeneration);
	}
}
