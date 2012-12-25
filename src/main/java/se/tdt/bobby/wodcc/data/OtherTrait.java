package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-14 01:09:22
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class OtherTrait implements Serializable, Cloneable  {

	private int mId;
	private String mName;
	private int mDots;

	public OtherTrait() {
	}

	public OtherTrait(int pId, String pName, int pDots) {
		mId = pId;
		mName = pName;
		mDots = pDots;
	}

	public OtherTrait(int pId, String pName) {
		mId = pId;
		mName = pName;
		mDots = 0;
	}

    public Object clone() {
        return new OtherTrait(mId, mName, mDots);
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
		return mName;
	}
}
