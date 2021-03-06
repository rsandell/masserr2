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

import com.github.jmkgreen.morphia.annotations.Embedded;
import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.PostLoad;
import com.github.jmkgreen.morphia.annotations.PrePersist;
import com.github.jmkgreen.morphia.annotations.Reference;
import com.github.jmkgreen.morphia.annotations.Transient;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import net.joinedminds.masserr.security.AccessControlled;
import net.joinedminds.masserr.security.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Access Control List
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Embedded
public class ACL<T extends AccessControlled> {
    private static final Logger logger = LoggerFactory.getLogger(ACL.class);

    @Embedded
    private Set<PrincipalAccess> matrix;
    @Reference
    private T target;

    protected ACL() {
    }

    public ACL(T target, Set<PrincipalAccess> matrix) {
        this.target = checkNotNull(target);
        this.matrix = checkNotNull(matrix);
    }

    public ACL(T target) {
        this(target, Collections.<PrincipalAccess>emptySet());
    }

    protected PrincipalAccess find(Principal principal) {
        if (matrix != null) {
            for(PrincipalAccess pa : matrix) {
                if (pa.principal.equals(principal)) {
                    return pa;
                }
            }
        }
        return null;
    }

    public boolean hasPermission(Principal principal, Permission test) {
        checkNotNull(principal);
        checkNotNull(test);
        PrincipalAccess pa = find(principal);
        if (pa != null && pa.hasPermission(test)) { //Direct or inferred permission?
            return true;
        } else if (principal.getMemberOf() != null){
            for(UserGroup group : principal.getMemberOf()) { //Indirect permission?
                if(hasPermission(group, test)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Embedded
    protected static class PrincipalAccess {
        @Reference
        private Principal principal;
        @Transient
        private Set<Permission> permissions;
        private String[] permissionStrings;

        PrincipalAccess() {
        }

        PrincipalAccess(Principal principal, Set<Permission> permissions) {
            checkNotNull(principal);
            checkNotNull(permissions);
            this.principal = principal;
            this.permissions = ImmutableSet.copyOf(permissions);
        }

        @PostLoad
        public void onLoad() {
            ImmutableSet.Builder<Permission> builder = ImmutableSet.builder();
            for(String id : permissionStrings) {
                Permission p = Permission.get(id);
                if (p != null) {
                    builder.add(p);
                } else {
                    logger.warn("Failed to find permission with id {}", id);
                }
            }
            permissions = builder.build();
        }

        @PrePersist
        public void prePersist() {
            String[] strs = new String[permissions.size()];
            Iterator<Permission> iterator = permissions.iterator();
            for (int i = 0; i < permissions.size(); i++) {
                strs[i] = iterator.next().getId();
            }
            permissionStrings = strs;
        }

        boolean hasPermission(Permission test) {
            checkNotNull(test);
            //First; does the principal have explicit permission
            for(Permission p : permissions) {
                if (test.equals(p)) {
                    return true;
                }
            }
            //Second; does it have any inferred permission
            if (test.getInferredBy() != null) {
                return hasPermission(test.getInferredBy());
            } else {
                return false;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PrincipalAccess that = (PrincipalAccess)o;

            if (!principal.equals(that.principal)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return principal.hashCode();
        }
    }
}
