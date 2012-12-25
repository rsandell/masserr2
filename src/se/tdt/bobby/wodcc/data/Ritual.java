package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-18 19:20:16
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class Ritual implements Serializable, Cloneable {

	private int mId;
	private String mName;
	private int mLevel;
    private RitualType mRitualType;
    private String mDescription;
    //public static final IntWithString[] LEVEL_NAMES = new se.tdt.bobby.wodcc.data.IntWithString[]{new se.tdt.bobby.wodcc.data.IntWithString(1, "Basic"), new se.tdt.bobby.wodcc.data.IntWithString(2, "Intermediate"), new se.tdt.bobby.wodcc.data.IntWithString(3, "Advanced"), new se.tdt.bobby.wodcc.data.IntWithString(4, "Superior")};


	public Ritual() {
	}

	public Ritual(int pId, String pName, int pLevel, RitualType pRitualType) {
		mId = pId;
		mName = pName;
		mLevel = pLevel;
        mRitualType = pRitualType;
    }

	public Ritual(int pId, String pName, int pLevel , RitualType pRitualType, String pDescription) {
		mId = pId;
		mName = pName;
		mLevel = pLevel;
        mRitualType = pRitualType;
        mDescription = pDescription;
	}

	public Ritual(int pId, String pName, RitualType pRitualType) {
		mId = pId;
		mName = pName;
        mRitualType = pRitualType;
    }

    public Object clone() {
        return new Ritual(mId, mName, mLevel, (RitualType) mRitualType.clone(), mDescription);
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

	public int getLevel() {
		return mLevel;
	}

	public void setLevel(int pLevel) {
		mLevel = pLevel;
	}

	public String toString() {
		return mName;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String pDescription) {
		mDescription = pDescription;
	}

    /*public se.tdt.bobby.wodcc.data.IntWithString getLevelName() {
        for (int i = 0; i < LEVEL_NAMES.length; i++) {
            se.tdt.bobby.wodcc.data.IntWithString levelName = LEVEL_NAMES[i];
            if(levelName.getNumber() == mLevel) {
                return levelName;
            }
        }
        return new se.tdt.bobby.wodcc.data.IntWithString(-1, "Undefined");
    }*/

    public RitualType getRitualType() {
        return mRitualType;
    }

    public void setRitualType(RitualType pRitualType) {
        mRitualType = pRitualType;
    }
}
