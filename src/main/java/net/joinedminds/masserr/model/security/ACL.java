package net.joinedminds.masserr.model.security;

import com.github.jmkgreen.morphia.annotations.Embedded;
import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.PostLoad;
import com.github.jmkgreen.morphia.annotations.PrePersist;
import com.github.jmkgreen.morphia.annotations.Reference;
import com.github.jmkgreen.morphia.annotations.Transient;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import net.joinedminds.masserr.security.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Access Control List
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Entity
public abstract class ACL {
    private static final Logger logger = LoggerFactory.getLogger(ACL.class);

    @Embedded
    private Set<PrincipalAccess> matrix;

    protected ACL() {
    }

    protected ACL(Set<PrincipalAccess> matrix) {
        checkNotNull(matrix);
        this.matrix = matrix;
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
