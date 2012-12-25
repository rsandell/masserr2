package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-11 14:44:54
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class Discipline implements Serializable, Cloneable  {
	private int mId;
	private String mName;
	private boolean mOfClan;
	private int mDots;
	private se.tdt.bobby.wodcc.data.Ability mRetestAbility = null;

	public Discipline() {
	}

	public Discipline(int pId, String pName, boolean pOfClan, int pDots, se.tdt.bobby.wodcc.data.Ability pRetestAbility) {
		mId = pId;
		mName = pName;
		mOfClan = pOfClan;
		mDots = pDots;
		mRetestAbility = pRetestAbility;
	}

	public Discipline(int pId, String pName, boolean pOfClan, int pDots) {
		mId = pId;
		mName = pName;
		mOfClan = pOfClan;
		mDots = pDots;
	}

	public Discipline(int pId, String pName, se.tdt.bobby.wodcc.data.Ability pRetestAbility) {
		mId = pId;
		mName = pName;
		mRetestAbility = pRetestAbility;
		mOfClan = false;
		mDots = 0;
	}

	public Discipline(int pId, String pName) {
		mId = pId;
		mName = pName;
		mOfClan = false;
		mDots = 0;
	}

    public Object clone() {
        return new Discipline(mId, mName, mOfClan, mDots,  (se.tdt.bobby.wodcc.data.Ability) mRetestAbility.clone());
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

	public boolean isOfClan() {
		return mOfClan;
	}

	public int getDots() {
		return mDots;
	}

	public void setOfClan(boolean pOfClan) {
		mOfClan = pOfClan;
	}

	public void setDots(int pDots) {
		mDots = pDots;
	}

	public String toString() {
		return mName;
	}

	public se.tdt.bobby.wodcc.data.Ability getRetestAbility() {
		return mRetestAbility;
	}

	public void setRetestAbility(se.tdt.bobby.wodcc.data.Ability pRetestAbility) {
		mRetestAbility = pRetestAbility;
	}
}
