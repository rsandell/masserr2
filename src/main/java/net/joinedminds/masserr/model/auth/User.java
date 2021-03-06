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

package net.joinedminds.masserr.model.auth;

import com.github.jmkgreen.morphia.annotations.Embedded;
import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Reference;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.model.Campaign;
import net.joinedminds.masserr.model.security.Principal;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 18:00:46
 *
 * @author <a href="mailto:sandell.robert@gmail.com>Robert Sandell</a>"
 */
@Entity
public class User extends Principal implements Serializable {

    @Id
    private ObjectId objectId;
    @Reference
    private Campaign campaign;
    private String fullName;
    private Set<String> emails;
    private String description;
    private Date lastLogin = null;
    @Embedded
    private List<OAuthIdentity> identities;

    /**
     * For serialization
     */
    public User() {
    }

    public User(Campaign campaign, String fullName, Set<String> emails, String description, List<OAuthIdentity> identities) {
        this.campaign = campaign;
        this.fullName = fullName;
        this.emails = emails;
        this.description = description;
        this.identities = identities;
    }

    public String getId() {
        return Functions.toString(objectId);
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


    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date pLastLogin) {
        lastLogin = pLastLogin;
    }

}
