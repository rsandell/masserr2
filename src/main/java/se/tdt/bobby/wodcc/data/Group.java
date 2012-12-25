package se.tdt.bobby.wodcc.data;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-27 21:43:06
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class Group implements Serializable, Cloneable {
    private int mId;
	private String mName;
	private Date mDate;
	private String mDescription;
	private String mType;
	private List mRoles;

    /**
     * For serialisation
     */
    public Group() {
    }

	public Group(int pId, String pName, Date pDate, String pDescription, String pType) {
		mId = pId;
		mName = pName;
		mDate = pDate;
		mDescription = pDescription;
		mType = pType;
		mRoles = new ArrayList();
	}

	public Group(String pName, Date pDate, String pDescription, String pType) {
		mId = -1;
		mName = pName;
		mDate = pDate;
		mDescription = pDescription;
		mType = pType;
		mRoles = new ArrayList();
	}

    public Object clone() {
        return new Group(mId, mName, new Date(mDate.getTime()), mDescription, mType);
    }

	public List getRoles() {
		return mRoles;
	}

	public void setRoles(List pRoles) {
		mRoles = pRoles;
	}

	public void addRole(Role pRole) {
		mRoles.add(pRole);
	}

	public String toString() {
		return mName;
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
}
