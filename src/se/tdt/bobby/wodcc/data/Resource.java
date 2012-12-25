package se.tdt.bobby.wodcc.data;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


/**
 * Description
 *
 * 
 * Created: 2004-feb-03 00:34:49
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class Resource implements Serializable, Cloneable {
	private int mId;
	private String mName;
    private String mDescription;
    private int mIncome;
	private int mPercent;
	private List mInfluences;
	private float mCost;
    private String mType;
    private se.tdt.bobby.wodcc.data.Domain mDomain;

	public Resource() {
	}

	public Resource(int pId, String pName, String pDescription, int pIncome, int pPercent, float pCost, String pType) {
		mId = pId;
		mName = pName;
		mDescription = pDescription;
		mIncome = pIncome;
		mPercent = pPercent;
		mCost = pCost;
        mType = pType;
    }

    public Resource(int pId, String pName, String pDescription, int pIncome, int pPercent, List pInfluences, float pCost, se.tdt.bobby.wodcc.data.Domain pDomain, String pType) {
        mId = pId;
        mName = pName;
        mDescription = pDescription;
        mIncome = pIncome;
        mPercent = pPercent;
        mInfluences = pInfluences;
        mCost = pCost;
        mDomain = pDomain;
        mType = pType;
    }

    public Resource(String pName, String pDescription, int pIncome, int pPercent, List pInfluences, float pCost, se.tdt.bobby.wodcc.data.Domain pDomain, String pType) {
        mName = pName;
        mDescription = pDescription;
        mIncome = pIncome;
        mPercent = pPercent;
        mInfluences = pInfluences;
        mCost = pCost;
        mDomain = pDomain;
        mType = pType;
    }

	public Resource(int pId, String pName, String pDescription, int pIncome, float pCost, String pType) {
		mId = pId;
		mName = pName;
		mDescription = pDescription;
		mIncome = pIncome;
		mCost = pCost;
        mType = pType;
        mPercent = 0;
	}

	public Resource(String pName, String pDescription, int pIncome, float pCost, String pType) {
		mName = pName;
		mDescription = pDescription;
		mIncome = pIncome;
		mCost = pCost;
        mType = pType;
        mId = -1;
		mPercent = 0;
	}

    public Object clone() {
        List infl = new ArrayList(mInfluences.size()+1);
        for (int i = 0; i < mInfluences.size(); i++) {
            Influence influence = (Influence) mInfluences.get(i);
            infl.add(influence.clone());
        }
        return new Resource(mId, mName, mDescription, mIncome, mPercent, infl, mCost, mDomain, mType);
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

	public int getIncome() {
		return mIncome;
	}

	public void setIncome(int pIncome) {
		mIncome = pIncome;
	}

	public int getPercent() {
		return mPercent;
	}

	public void setPercent(int pPercent) {
		mPercent = pPercent;
	}

	public String toString() {
		return mName;
	}

	public void setInfluences(List pInfluences) {
		mInfluences = pInfluences;
	}

	public List getInfluences() {
		return mInfluences;
	}

	public float getCost() {
		return mCost;
	}

	public void setCost(float pCost) {
		mCost = pCost;
	}

    public se.tdt.bobby.wodcc.data.Domain getDomain() {
        return mDomain;
    }

    public void setDomain(se.tdt.bobby.wodcc.data.Domain pDomain) {
        mDomain = pDomain;
    }

    public String getType() {
        return mType;
    }

    public void setType(String pType) {
        mType = pType;
    }
}
