package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-11 19:31:04
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class Path implements Serializable, Cloneable  {

	private int mId;
	private String mName;
	private int mDots;

	public Path() {
	}

	public Path(int pId, String pName, int pDots) {
		mId = pId;
		mName = pName;
		mDots = pDots;
	}

	public Path(int pId, String pName) {
		mId = pId;
		mName = pName;
		mDots = 0;
	}

    public Object clone() {
        return new Path(mId, mName, mDots);
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
