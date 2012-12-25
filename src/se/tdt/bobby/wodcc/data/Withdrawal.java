package se.tdt.bobby.wodcc.data;

import java.text.NumberFormat;

/**
 * Description
 *
 * 
 * Created: 2004-feb-13 17:41:20
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class Withdrawal implements java.io.Serializable, Cloneable {
	private BankAccount mBankAccount;
	private long mAmmount;
    private static NumberFormat mFormat = NumberFormat.getCurrencyInstance();

	public Withdrawal(BankAccount pBankAccount, long pAmmount) {
		mBankAccount = pBankAccount;
		mAmmount = pAmmount;
	}

    /**
     * For serialization
     */
    public Withdrawal() {
    }

    public Object clone() {
        return new Withdrawal((BankAccount) mBankAccount.clone(), mAmmount);
    }

	public BankAccount getBankAccount() {
		return mBankAccount;
	}

	public void setBankAccount(BankAccount pBankAccount) {
		mBankAccount = pBankAccount;
	}

	public long getAmmount() {
		return mAmmount;
	}

	public void setAmmount(long pAmmount) {
		mAmmount = pAmmount;
	}

	public String toString() {
		return mFormat.format(mAmmount) + " from " + mBankAccount.toString();
	}
}
