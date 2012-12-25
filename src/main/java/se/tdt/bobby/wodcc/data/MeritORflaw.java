package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-13 20:48:42
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class MeritORflaw implements Serializable, Cloneable {

	private int mId;
	private String mName;
	private int mPoints;
	private String mNote;

	public MeritORflaw() {
	}

	public MeritORflaw(int pId, String pName, int pPoints, String pNote) {
		mId = pId;
		mName = pName;
		mPoints = pPoints;
		mNote = pNote;
	}

	public MeritORflaw(int pId, String pName, int pPoints) {
		mId = pId;
		mName = pName;
		mPoints = pPoints;
		mNote = "";
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

	public int getPoints() {
		return mPoints;
	}

	public void setPoints(int pPoints) {
		mPoints = pPoints;
	}

	public String getNote() {
		return mNote;
	}

	public void setNote(String pNote) {
		mNote = pNote;
	}

	public String toString() {
		return mName + " (" + mPoints + ")";
	}

	public Object clone() {
		return new MeritORflaw(mId, mName, mPoints, mNote);
	}
}
