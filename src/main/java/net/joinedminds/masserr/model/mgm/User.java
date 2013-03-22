/*
 * The MIT License
 *
 * Copyright (c) 2004-2012-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.joinedminds.masserr.model.mgm;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Reference;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.model.Domain;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 18:00:46
 *
 * @author <a href="mailto:sandell.robert@gmail.com>Robert Sandell</a>"
 */
@Entity
public class User implements Serializable {

    @Id
    private ObjectId objectId;
    private String userName;
    private String password;
    @Reference
    private Domain domain;
    private UserRights userRights;
    private String fullName;
    private String description;
    private Date lastLogin = null;
    private boolean otherDomainIsAll = false;
    @Reference
    private List<Domain> otherDomains;

    /**
     * For serialization
     */
    public User() {
    }

    public User(String pId, String pUserName, String pPassword, Domain pDomain, UserRights pUserRights, String pFullName, String pDescription) {
        objectId = new ObjectId(pId);
        userName = pUserName;
        password = pPassword;
        domain = pDomain;
        userRights = pUserRights;
        fullName = pFullName;
        description = pDescription;
    }

    public User(String pId, String pUserName, String pPassword, Domain pDomain, String pUserRights, String pFullName, String pDescription) {
        objectId = new ObjectId(pId);
        userName = pUserName;
        password = pPassword;
        domain = pDomain;
        fullName = pFullName;
        description = pDescription;
        if (pUserRights == null) {
            pUserRights = "";
        }
        userRights = new UserRights(pUserRights);
    }

    public User(String pId, String pUserName, String pPassword, Domain pDomain, UserRights pUserRights, String pFullName) {
        objectId = new ObjectId(pId);
        userName = pUserName;
        password = pPassword;
        domain = pDomain;
        userRights = pUserRights;
        fullName = pFullName;
        description = "";
    }

    public User(String pId, String pUserName, String pPassword, Domain pDomain, String pUserRights, String pFullName) {
        objectId = new ObjectId(pId);
        userName = pUserName;
        password = pPassword;
        domain = pDomain;
        fullName = pFullName;
        description = "";
        if (pUserRights == null) {
            pUserRights = "";
        }
        userRights = new UserRights(pUserRights);
    }

    public User(String pId, String pUserName, Domain pDomain, UserRights pUserRights) {
        objectId = new ObjectId(pId);
        userName = pUserName;
        domain = pDomain;
        userRights = pUserRights;
    }

    public User(String pId, String pUserName, Domain pDomain, String pUserRights) {
        objectId = new ObjectId(pId);
        userName = pUserName;
        domain = pDomain;
        if (pUserRights == null) {
            pUserRights = "";
        }
        userRights = new UserRights(pUserRights);
    }

    public User(String pId, String pUserName, String pPassword, Domain pDomain, UserRights pUserRights, String pFullName, String pDescription, Date pLastLogin, boolean pOtherDomainIsAll, Vector<Domain> pOtherDomains) {
        objectId = new ObjectId(pId);
        userName = pUserName;
        password = pPassword;
        domain = pDomain;
        userRights = pUserRights;
        fullName = pFullName;
        description = pDescription;
        lastLogin = pLastLogin;
        otherDomainIsAll = pOtherDomainIsAll;
        otherDomains = pOtherDomains;
    }

    public User(String pId, String pUserName, String pPassword, Domain pDomain, String pUserRights, String pFullName, String pDescription, Date pLastLogin, boolean pOtherDomainIsAll, Vector<Domain> pOtherDomains) {
        objectId = new ObjectId(pId);
        userName = pUserName;
        password = pPassword;
        domain = pDomain;
        userRights = new UserRights(pUserRights);
        fullName = pFullName;
        description = pDescription;
        lastLogin = pLastLogin;
        otherDomainIsAll = pOtherDomainIsAll;
        otherDomains = pOtherDomains;
    }

    public String getId() {
        return Functions.toString(objectId);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String pUserName) {
        userName = pUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pPassword) {
        password = pPassword;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain pDomain) {
        domain = pDomain;
    }

    public UserRights getUserRights() {
        return userRights;
    }

    public void setUserRights(UserRights pUserRights) {
        userRights = pUserRights;
    }

    public void setUserRights(String pUserRights) {
        userRights = new UserRights(pUserRights);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String pFullName) {
        fullName = pFullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public String toString() {
        return userName;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date pLastLogin) {
        lastLogin = pLastLogin;
    }

    public boolean isOtherDomainIsAll() {
        return otherDomainIsAll;
    }

    public void setOtherDomainIsAll(boolean pOtherDomainIsAll) {
        otherDomainIsAll = pOtherDomainIsAll;
    }

    public List<Domain> getOtherDomains() {
        return otherDomains;
    }

    public void setOtherDomains(List<Domain> pOtherDomains) {
        otherDomains = pOtherDomains;
    }

    public boolean hasDomain(Domain pDomain) {
        if (pDomain.getId() == domain.getId()) {
            return true;
        }
        if (otherDomainIsAll) {
            return true;
        }
        if (otherDomains != null) {
            for (int i = 0; i < otherDomains.size(); i++) {
                Domain domain = otherDomains.get(i);
                if (domain.getId() == pDomain.getId()) {
                    return true;
                }
            }
        }
        return false;
    }
}
