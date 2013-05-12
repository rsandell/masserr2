/*
 * The MIT License
 *
 * Copyright (c) 2013-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
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

package net.joinedminds.masserr.model.security;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Indexed;
import com.github.jmkgreen.morphia.annotations.Reference;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.model.NamedIdentifiable;
import org.bson.types.ObjectId;

import java.util.Set;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Entity
public class UserGroup extends Principal implements NamedIdentifiable {
    @Id
    private ObjectId objectId;
    @Indexed(unique = true)
    private String name;
    private String description;
    @Reference(lazy = true)
    private Set<Principal> members;

    public UserGroup() {
    }

    public UserGroup(String name, String description, Set<Principal> members) {
        this.name = name;
        this.description = description;
        this.members = members;
    }

    public String getId() {
        return Functions.toString(objectId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Principal> getMembers() {
        return members;
    }

    public void setMembers(Set<Principal> members) {
        this.members = members;
        for (Principal p : members) {
            p.addMemberOf(this);
        }
    }

    public boolean addMember(Principal p) {
        boolean added = members.add(p);
        p.addMemberOf(this);
        return added;
    }
}
