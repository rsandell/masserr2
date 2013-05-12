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
import net.joinedminds.masserr.Messages;
import org.jvnet.localizer.Localizable;

import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public final class Permission {
    /*package*/ static final Set<Permission> ALL = new CopyOnWriteArraySet<>();
    private final String id;
    private final PermissionGroup group;
    private final Localizable name;
    private final Localizable description;
    private final Permission inferredBy;

    public Permission(PermissionGroup group, Localizable name, Localizable description, Permission inferredBy) {
        this.group = checkNotNull(group);
        this.name = checkNotNull(name);
        this.inferredBy = inferredBy;
        this.description = description == null ? Messages._empty() : description;
        this.id = group.getId() + "." + name.toString(Locale.US);
        group.addPermission(this);
        ALL.add(this);
    }

    public Permission(PermissionGroup group, Localizable name, Localizable description) {
        this(group, name, description, null);
    }

    public Permission(PermissionGroup group, Localizable name) {
        this(group, name, null);
    }

    public PermissionGroup getGroup() {
        return group;
    }

    public Localizable getName() {
        return name;
    }

    public Localizable getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public Permission getInferredBy() {
        return inferredBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission)o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return 31 * getId().hashCode();
    }

    public static Set<Permission> all() {
        return ImmutableSet.copyOf(ALL);
    }

    public static Permission get(String id) {
        for (Permission p : ALL) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
}
