package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-11 01:21:56
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class IntWithString implements Serializable, Cloneable  {

	private int mNumber;
	private String mString;

	public IntWithString() {
	}

	public IntWithString(int pNumber, String pString) {
		mNumber = pNumber;
		mString = pString;
	}

	public IntWithString(String pString, int pNumber) {
		mString = pString;
		mNumber = pNumber;
	}

    public Object clone() {
        return new IntWithString(mNumber, mString);
    }

	public int getNumber() {
		return mNumber;
	}

	public void setNumber(int pNumber) {
		mNumber = pNumber;
	}

	public String getString() {
		return mString;
	}

	public void setString(String pString) {
		mString = pString;
	}

	/**
	 * Returns a string representation of the object. In general, the
	 * <code>toString</code> method returns a string that
	 * "textually represents" this object. The result should
	 * be a concise but informative representation that is easy for a
	 * person to read.
	 * It is recommended that all subclasses override this method.
	 * <p>
	 * The <code>toString</code> method for class <code>Object</code>
	 * returns a string consisting of the name of the class of which the
	 * object is an instance, the at-sign character `<code>@</code>', and
	 * the unsigned hexadecimal representation of the hash code of the
	 * object. In other words, this method returns a string equal to the
	 * value of:
	 * <blockquote>
	 * <pre>
	 * getClass().getName() + '@' + Integer.toHexString(hashCode())
	 * </pre></blockquote>
	 *
	 * @return  a string representation of the object.
	 */
	public String toString() {
		return mString;
	}
}
