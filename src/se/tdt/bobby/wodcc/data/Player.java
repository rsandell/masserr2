package se.tdt.bobby.wodcc.data;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-jun-29 19:57:15
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class Player implements Serializable, Cloneable{
    private int mId;
    private String mName;
    private String mAddress;
    private String mPhone;
    private String mEmail;
    private int mXP;
    private Vector<Role> mRoles;
    private Vector<Experience> mExperienceList;

    public Player(int pId, String pName, String pAddress, String pPhone, String pEmail) {
        mId = pId;
        mName = pName;
        mAddress = pAddress;
        mPhone = pPhone;
        mEmail = pEmail;
    }

    public Player(int pId, String pName) {
        mId = pId;
        mName = pName;
        mAddress = "";
        mPhone = "";
        mEmail = "";
    }

    public Player() {
    }

    public Object clone() {
        return new Player(mId, mName, mAddress, mPhone, mEmail);
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

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String pAddress) {
        mAddress = pAddress;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String pPhone) {
        mPhone = pPhone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String pEmail) {
        mEmail = pEmail;
    }

    public String toString() {
        return mName;
    }

    public int getXP() {
        return mXP;
    }

    public void setXP(int pXP) {
        mXP = pXP;
    }

    public Vector<Role> getRoles() {
        return mRoles;
    }

    public void setRoles(Vector<Role> pRoles) {
        mRoles = pRoles;
    }

    public Vector<Experience> getExperienceList() {
        return mExperienceList;
    }

    public void setExperienceList(Vector<Experience> pExperienceList) {
        mExperienceList = pExperienceList;
    }
}
