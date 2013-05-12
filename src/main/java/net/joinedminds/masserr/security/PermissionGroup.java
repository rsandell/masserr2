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

package net.joinedminds.masserr.security;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.jvnet.localizer.Localizable;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public final class PermissionGroup {

    /*package*/ static final Set<PermissionGroup> ALL = new CopyOnWriteArraySet<>();
    /*package*/ static final Multimap<Class<? extends AccessControlled>, PermissionGroup> ALL_BY_CLASS = TreeMultimap.create(new OwnerComparator(), new PermissionGroupComparator());

    private final String id;
    private final Class<? extends AccessControlled> owner;
    private final Localizable name;
    private final Set<Permission> permissions;

    public PermissionGroup(Class<? extends AccessControlled> owner, Localizable name) {
        this.owner = checkNotNull(owner);
        this.name = checkNotNull(name);
        this.id = owner.getName() + "." + name.toString(Locale.US);
        permissions = Collections.synchronizedSet(new HashSet<Permission>());
        ALL.add(this);
        ALL_BY_CLASS.put(owner, this);
    }

    public Class<? extends AccessControlled> getOwner() {
        return owner;
    }

    public Localizable getName() {
        return name;
    }

    public Set<Permission> getPermissions() {
        return ImmutableSet.copyOf(permissions);
    }

    /*package*/ boolean addPermission(Permission permission) {
        return permissions.add(permission);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionGroup that = (PermissionGroup)o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return 31 * getId().hashCode();
    }

    public static Set<PermissionGroup> all() {
        return ImmutableSet.copyOf(ALL);
    }

    public static Set<PermissionGroup> getGroups(Class<? extends AccessControlled> owner) {
        return ImmutableSet.copyOf(ALL_BY_CLASS.get(owner));
    }

    public static PermissionGroup get(String id) {
        for (PermissionGroup g : ALL) {
            if (g.getId().equals(id)) {
                return g;
            }
        }
        return null;
    }

    private static class OwnerComparator implements Comparator<Class<? extends AccessControlled>> {
        @Override
        public int compare(Class<? extends AccessControlled> o1, Class<? extends AccessControlled> o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    private static class PermissionGroupComparator implements Comparator<PermissionGroup> {
        @Override
        public int compare(PermissionGroup o1, PermissionGroup o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }
}
