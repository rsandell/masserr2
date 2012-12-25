package se.tdt.bobby.wodcc.data;

import java.io.Serializable;
import java.sql.Date;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-25 17:34:51
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class Plot implements Serializable, Cloneable{
    private int mId;
    private String mTitle;
    private String mDescription;
    private Date mCreated;
    private String mPositive;
    private String mNegative;
    private boolean mDone;
    private Domain mDomain;
    private String mSLdescription;


    public Plot(int pId, String pTitle, String pDescription, Date pCreated, String pPositive, String pNegative, boolean pDone, Domain pDomain, String pSLdescription) {
        mId = pId;
        mTitle = pTitle;
        mDescription = pDescription;
        mCreated = pCreated;
        mPositive = pPositive;
        mNegative = pNegative;
        mDone = pDone;
        mDomain = pDomain;
        mSLdescription = pSLdescription;
    }

    public Plot(String pTitle, String pDescription, Date pCreated, String pPositive, String pNegative, boolean pDone, Domain pDomain, String pSLdescription) {
        mTitle = pTitle;
        mDescription = pDescription;
        mCreated = pCreated;
        mPositive = pPositive;
        mNegative = pNegative;
        mDone = pDone;
        mDomain = pDomain;
        mSLdescription = pSLdescription;
        mId = -1;

    }

    public Plot(String pTitle, String pDescription, Date pCreated, String pPositive, String pNegative, Domain pDomain, String pSLdescription) {
        mTitle = pTitle;
        mDescription = pDescription;
        mCreated = pCreated;
        mPositive = pPositive;
        mNegative = pNegative;
        mDomain = pDomain;
        mSLdescription = pSLdescription;
    }

    public Plot(int pId, String pTitle, String pDescription, Date pCreated, Domain pDomain, String pSLdescription) {
        mId = pId;
        mTitle = pTitle;
        mDescription = pDescription;
        mCreated = pCreated;
        mDomain = pDomain;
        mSLdescription = pSLdescription;
    }

    public Plot(int pId, String pTitle, Domain pDomain) {
        mId = pId;
        mTitle = pTitle;
        mDomain = pDomain;
    }

    public int getId() {
        return mId;
    }

    public void setId(int pId) {
        mId = pId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String pTitle) {
        mTitle = pTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String pDescription) {
        mDescription = pDescription;
    }

    public Date getCreated() {
        return mCreated;
    }

    public void setCreated(Date pCreated) {
        mCreated = pCreated;
    }

    public String getPositive() {
        return mPositive;
    }

    public void setPositive(String pPositive) {
        mPositive = pPositive;
    }

    public String getNegative() {
        return mNegative;
    }

    public void setNegative(String pNegative) {
        mNegative = pNegative;
    }

    public boolean isDone() {
        return mDone;
    }

    public Object clone() {
        return new Plot(mId, mTitle, mDescription, (Date) mCreated.clone(), mPositive, mNegative, mDone, (Domain) mDomain.clone(), mSLdescription);
    }

    public void setDone(boolean pDone) {
        mDone = pDone;
    }

    public String toString() {
        return mId + ": " + mTitle;
    }

    public Domain getDomain() {
        return mDomain;
    }

    public void setDomain(Domain pDomain) {
        mDomain = pDomain;
    }

    public String getSLdescription() {
        return mSLdescription;
    }

    public void setSLdescription(String pSLdescription) {
        mSLdescription = pSLdescription;
    }
}
