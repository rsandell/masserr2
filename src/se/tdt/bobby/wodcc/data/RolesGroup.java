package se.tdt.bobby.wodcc.data;

import java.util.Date;
import java.io.Serializable;

/**
 * Description
 *
 * Created: 2004-feb-17 21:10:37
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class RolesGroup implements Serializable {
    private int mId;
    private String mName;
    private Date mDate;
    private String mDescription;
    private String mType;

    /**
     * For serialization
     */
    public RolesGroup() {
    }

    public RolesGroup(int pId, String pName, Date pDate, String pDescription, String pType) {
        mId = pId;
        mName = pName;
        mDate = pDate;
        mDescription = pDescription;
        mType = pType;
    }

    public RolesGroup(String pName, Date pDate, String pDescription, String pType) {
        mName = pName;
        mDate = pDate;
        mDescription = pDescription;
        mType = pType;
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date pDate) {
        mDate = pDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String pDescription) {
        mDescription = pDescription;
    }

    public String getType() {
        return mType;
    }

    public void setType(String pType) {
        mType = pType;
    }

    public String toString() {
        return mName;
    }
}
