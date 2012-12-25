package se.tdt.bobby.wodcc.data;

import java.text.NumberFormat;
import java.io.Serializable;

/**
 * Description
 *
 *
 * Created: 2004-feb-06 13:48:10
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class BankAccount implements Serializable, Cloneable {
	private int mId;
	private String mOwnerName;
	private double mAmmount;
	private boolean mIncome;
	private boolean mActive;

    /**
     * For serialization
     */
    public BankAccount() {
    }

	public BankAccount(int pId, String pOwnerName, double pAmmount, boolean pIncome, boolean pActive) {
		mId = pId;
		mOwnerName = pOwnerName;
		mAmmount = pAmmount;
		mIncome = pIncome;
		mActive = pActive;
	}

	public BankAccount(String pOwnerName, float pAmmount, boolean pIncome) {
		mOwnerName = pOwnerName;
		mAmmount = pAmmount;
		mIncome = pIncome;
		mId = -1;
		mActive = true;
	}

    public Object clone(){
        return new BankAccount(mId, mOwnerName, mAmmount, mIncome, mActive);
    }

	public int getId() {
		return mId;
	}

	public void setId(int pId) {
		mId = pId;
	}

	public String getOwnerName() {
		return mOwnerName;
	}

	public void setOwnerName(String pOwnerName) {
		mOwnerName = pOwnerName;
	}

	public double getAmmount() {
		return mAmmount;
	}

	public void setAmmount(double pAmmount) {
		mAmmount = pAmmount;
	}

	public boolean isIncome() {
		return mIncome;
	}

	public void setIncome(boolean pIncome) {
		mIncome = pIncome;
	}

	public boolean isActive() {
		return mActive;
	}

	public void setActive(boolean pActive) {
		mActive = pActive;
	}

	public String toString() {
		String str = "";
		if (mId < 10) {
			str = "0" + mId + ": ";
		}
		else {
			str = mId + ": ";
		}
		str += mOwnerName + " ( " + NumberFormat.getCurrencyInstance().format(mAmmount) + " )";
		return str;
	}
}
