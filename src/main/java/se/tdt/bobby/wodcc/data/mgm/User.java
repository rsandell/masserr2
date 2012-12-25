package se.tdt.bobby.wodcc.data.mgm;

import se.tdt.bobby.wodcc.data.Domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 18:00:46
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class User implements Serializable, Cloneable {
    private int mId;
    private String mUserName;
    private String mPassword;
    private Domain mDomain;
    private UserRights mUserRights;
    private String mFullName;
    private String mDescription;
    private Date mLastLogin = null;
    private boolean mOtherDomainIsAll = false;
    private Vector<Domain> mOtherDomains = new Vector<Domain>(1);

    /**
     * For serialization
     */
    public User() {
    }

    public User(int pId, String pUserName, String pPassword, Domain pDomain, UserRights pUserRights, String pFullName, String pDescription) {
        mId = pId;
        mUserName = pUserName;
        mPassword = pPassword;
        mDomain = pDomain;
        mUserRights = pUserRights;
        mFullName = pFullName;
        mDescription = pDescription;
    }

    public User(int pId, String pUserName, String pPassword, Domain pDomain, String pUserRights, String pFullName, String pDescription) {
        mId = pId;
        mUserName = pUserName;
        mPassword = pPassword;
        mDomain = pDomain;
        mFullName = pFullName;
        mDescription = pDescription;
        if (pUserRights == null) {
            pUserRights = "";
        }
        mUserRights = new UserRights(pUserRights);
    }

    public User(int pId, String pUserName, String pPassword, Domain pDomain, UserRights pUserRights, String pFullName) {
        mId = pId;
        mUserName = pUserName;
        mPassword = pPassword;
        mDomain = pDomain;
        mUserRights = pUserRights;
        mFullName = pFullName;
        mDescription = "";
    }

    public User(int pId, String pUserName, String pPassword, Domain pDomain, String pUserRights, String pFullName) {
        mId = pId;
        mUserName = pUserName;
        mPassword = pPassword;
        mDomain = pDomain;
        mFullName = pFullName;
        mDescription = "";
        if (pUserRights == null) {
            pUserRights = "";
        }
        mUserRights = new UserRights(pUserRights);
    }

    public User(int pId, String pUserName, Domain pDomain, UserRights pUserRights) {
        mId = pId;
        mUserName = pUserName;
        mDomain = pDomain;
        mUserRights = pUserRights;
    }

    public User(int pId, String pUserName, Domain pDomain, String pUserRights) {
        mId = pId;
        mUserName = pUserName;
        mDomain = pDomain;
        if (pUserRights == null) {
            pUserRights = "";
        }
        mUserRights = new UserRights(pUserRights);
    }

    public User(int pId, String pUserName, String pPassword, Domain pDomain, UserRights pUserRights, String pFullName, String pDescription, Date pLastLogin, boolean pOtherDomainIsAll, Vector<Domain> pOtherDomains) {
        mId = pId;
        mUserName = pUserName;
        mPassword = pPassword;
        mDomain = pDomain;
        mUserRights = pUserRights;
        mFullName = pFullName;
        mDescription = pDescription;
        mLastLogin = pLastLogin;
        mOtherDomainIsAll = pOtherDomainIsAll;
        mOtherDomains = pOtherDomains;
    }

    public User(int pId, String pUserName, String pPassword, Domain pDomain, String pUserRights, String pFullName, String pDescription, Date pLastLogin, boolean pOtherDomainIsAll, Vector<Domain> pOtherDomains) {
        mId = pId;
        mUserName = pUserName;
        mPassword = pPassword;
        mDomain = pDomain;
        mUserRights = new UserRights(pUserRights);
        mFullName = pFullName;
        mDescription = pDescription;
        mLastLogin = pLastLogin;
        mOtherDomainIsAll = pOtherDomainIsAll;
        mOtherDomains = pOtherDomains;
    }

    public Object clone() {
        return new User(mId, mUserName, mPassword, (Domain) mDomain.clone(), mUserRights.toString(), mFullName, mDescription, mLastLogin, mOtherDomainIsAll, (Vector<Domain>) mOtherDomains.clone());
    }

    public int getId() {
        return mId;
    }

    public void setId(int pId) {
        mId = pId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String pUserName) {
        mUserName = pUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String pPassword) {
        mPassword = pPassword;
    }

    public Domain getDomain() {
        return mDomain;
    }

    public void setDomain(Domain pDomain) {
        mDomain = pDomain;
    }

    public UserRights getUserRights() {
        return mUserRights;
    }

    public void setUserRights(UserRights pUserRights) {
        mUserRights = pUserRights;
    }

    public void setUserRights(String pUserRights) {
        mUserRights = new UserRights(pUserRights);
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String pFullName) {
        mFullName = pFullName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String pDescription) {
        mDescription = pDescription;
    }

    public String toString() {
        return mUserName;
    }

    public Date getLastLogin() {
        return mLastLogin;
    }

    public void setLastLogin(Date pLastLogin) {
        mLastLogin = pLastLogin;
    }

    public boolean isOtherDomainIsAll() {
        return mOtherDomainIsAll;
    }

    public void setOtherDomainIsAll(boolean pOtherDomainIsAll) {
        mOtherDomainIsAll = pOtherDomainIsAll;
    }

    public Vector<Domain> getOtherDomains() {
        return mOtherDomains;
    }

    public void setOtherDomains(Vector<Domain> pOtherDomains) {
        mOtherDomains = pOtherDomains;
    }

    public boolean hasDomain(Domain pDomain) {
        if(pDomain.getId() == mDomain.getId()) {
            return true;
        }
        if(mOtherDomainIsAll) {
            return true;
        }
        if(mOtherDomains != null) {
            for (int i = 0; i < mOtherDomains.size(); i++) {
                Domain domain = mOtherDomains.get(i);
                if(domain.getId() == pDomain.getId()) {
                    return true;
                }
            }
        }
        return false;
    }
}
