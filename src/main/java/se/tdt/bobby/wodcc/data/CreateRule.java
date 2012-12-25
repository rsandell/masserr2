package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-feb-02 10:01:20
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class CreateRule implements Serializable, Cloneable {
	int mId;
	int mYearMin;
	int mYearMax;
	int mDisciplines;
    int mAttributes;
	int mAbilities;

	public CreateRule() {
	}

	public CreateRule(int pId, int pYearMin, int pYearMax, int pDisciplines, int pAttributes, int pAbilities) {
		mId = pId;
		mYearMin = pYearMin;
		mYearMax = pYearMax;
		mDisciplines = pDisciplines;
		mAttributes = pAttributes;
		mAbilities = pAbilities;
	}

	public CreateRule(int pYearMin, int pYearMax, int pDisciplines, int pAttributes, int pAbilities) {
		mYearMin = pYearMin;
		mYearMax = pYearMax;
		mDisciplines = pDisciplines;
		mAttributes = pAttributes;
		mAbilities = pAbilities;
		mId = -1;
	}

    public Object clone() {
        return new CreateRule(mId, mYearMin, mYearMax, mDisciplines, mAttributes, mAbilities);
    }

	public int getId() {
		return mId;
	}

	public void setId(int pId) {
		mId = pId;
	}

	public int getYearMin() {
		return mYearMin;
	}

	public void setYearMin(int pYearMin) {
		mYearMin = pYearMin;
	}

	public int getYearMax() {
		return mYearMax;
	}

	public void setYearMax(int pYearMax) {
		mYearMax = pYearMax;
	}

	public int getDisciplines() {
		return mDisciplines;
	}

	public void setDisciplines(int pDisciplines) {
		mDisciplines = pDisciplines;
	}

	/**
	 * a.k. Traits
	 * @return
	 */
	public int getAttributes() {
		return mAttributes;
	}

	/**
	 * a.k. Traits
	 * @param pAttributes
	 */
	public void setAttributes(int pAttributes) {
		mAttributes = pAttributes;
	}

	public int getAbilities() {
		return mAbilities;
	}

	public void setAbilities(int pAbilities) {
		mAbilities = pAbilities;
	}
}
