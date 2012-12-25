package se.tdt.bobby.wodcc.data;

import se.tdt.bobby.wodcc.data.mgm.User;

import java.io.Serializable;
import java.util.Date;

/**
 * Description.
 * <p/>
 * Created: 2004-maj-18 16:18:17
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class Experience implements Serializable {
    private int mAmmount;
    private Date mDate;
    private String mReason;
    private User mSetByUser;

    public Experience(int pAmmount, Date pDate, String pReason) {
        mAmmount = pAmmount;
        mDate = pDate;
        mReason = pReason;
    }

    public Experience(int pAmmount, Date pDate, String pReason, User pSetByUser) {
        mAmmount = pAmmount;
        mDate = pDate;
        mReason = pReason;
        mSetByUser = pSetByUser;
    }

    /**
     * Only for Serialization
     */
    public Experience() {
    }

    public int getAmmount() {
        return mAmmount;
    }

    public void setAmmount(int pAmmount) {
        mAmmount = pAmmount;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date pDate) {
        mDate = pDate;
    }

    public String getReason() {
        return mReason;
    }

    public void setReason(String pReason) {
        mReason = pReason;
    }

    public User getSetByUser() {
        return mSetByUser;
    }

    public void setSetByUser(User pSetByUser) {
        mSetByUser = pSetByUser;
    }
}
