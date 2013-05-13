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

package net.joinedminds.masserr.model;

import com.github.jmkgreen.morphia.annotations.Embedded;
import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Indexed;
import com.github.jmkgreen.morphia.annotations.Reference;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.Messages;
import net.joinedminds.masserr.model.security.ACL;
import net.joinedminds.masserr.model.security.Principal;
import net.joinedminds.masserr.security.AccessControlled;
import net.joinedminds.masserr.security.Permission;
import net.joinedminds.masserr.security.PermissionGroup;
import org.bson.types.ObjectId;

/**
 * Description
 * <p/>
 * Created: 2004-mar-08 17:06:13
 *
 * @author <a href="mailto:sandell.robert@gmail.com">Robert "Bobby" Sandell</a>
 */
@Entity
public class Domain implements NamedIdentifiable, AccessControlled<Domain> {

    @Id
    private ObjectId objectId;
    @Indexed
    private String name;
    private String history;
    @Embedded
    private ACL<Domain> acl;

    public Domain() {
    }

    public Domain(String pName, String pHistory) {
        name = pName;
        history = pHistory;
        acl = new ACL<>(this);
    }

    public Domain(String pName) {
        name = pName;
        acl = new ACL<>(this);
    }

    @Override
    public String getId() {
        return Functions.toString(objectId);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String pHistory) {
        history = pHistory;
    }

    public String toString() {
        return name;
    }

    public static Domain idRef(String dId) {
        ObjectId id = Functions.toObjectId(dId);
        if(id != null) {
            Domain d = new Domain();
            d.objectId = id;
            return d;
        } else {
            return null;
        }
    }

    @Override
    public boolean hasPermission(Principal principal, Permission permission) {
        return getACL().hasPermission(principal, permission);
    }

    @Override
    public synchronized ACL<Domain> getACL() {
        if (acl == null) {
            acl = new ACL<>(this);
        }
        return acl;
    }

    public static final PermissionGroup PGROUP_GENERAL = new PermissionGroup(Domain.class, Messages._Domain_Permission_General());
    public static final Permission ADMINISTER = new Permission(PGROUP_GENERAL, Messages._Domain_Permission_General_Administer(), Messages._Domain_Permission_General_Administer_Description());
    public static final Permission CHANGE_NAME = new Permission(PGROUP_GENERAL, Messages._Domain_Permission_General_ChangeName(), ADMINISTER);
    public static final Permission EDIT_HISTORY = new Permission(PGROUP_GENERAL, Messages._Domain_Permission_General_EditHistory(), ADMINISTER);

    public static final PermissionGroup PGROUP_ROLES = new PermissionGroup(Domain.class, Messages._Domain_Permission_Role());
    public static final Permission ROLE_CREATE = new Permission(PGROUP_ROLES, Messages._Domain_Permission_Role_Create(), ADMINISTER);
    public static final Permission ROLE_LIST = new Permission(PGROUP_ROLES, Messages._Domain_Permission_Role_List(), Role.READ);
}
