package org.ddd.fundamental.core.domain.user;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.ddd.fundamental.core.domain.user.Role.*;
import static org.ddd.fundamental.core.utils.CommonUtils.requireNonBlank;

public final  class User {
    public static final User NOUSER = new User(null, null, null, null);
    public static final User ANONYMOUS_USER = NOUSER;
    private final String memberId;
    private final String name;
    private final String tenantId;
    private final Role role;

    private User(String memberId, String name, String tenantId, Role role) {
        this.memberId = memberId;
        this.name = name;
        this.tenantId = tenantId;
        this.role = role;
    }

    public static User humanUser(String memberId, String name, String tenantId, Role role) {
        requireNonBlank(memberId, "MemberId must not be blank.");
        requireNonBlank(name, "Name must not be blank.");
        requireNonBlank(tenantId, "TenantId must not be blank.");
        requireNonNull(role, "Role must not be null.");

        if (role == ROBOT) {
            throw new IllegalStateException("Human user should not have ROBOT role.");
        }

        return new User(memberId, name, tenantId, role);
    }

    public static User robotUser(String tenantId) {
        requireNonBlank(tenantId, "TenantId must not be blank.");

        return new User(null, null, tenantId, ROBOT);
    }

    public boolean isLoggedIn() {
        return internalIsLoggedIn();
    }

    public boolean isMryManageTenantUser() {
        return Objects.equals(this.tenantId, "MRY_MANAGE_TENANT_ID");
    }

    public void checkIsLoggedIn() {
        internalCheckLoggedIn();
    }

    public boolean isLoggedInFor(String tenantId) {
        requireNonBlank(tenantId, "TenantId must not be blank.");

        if (!internalIsLoggedIn()) {
            return false;
        }

        return isTenantFor(tenantId);
    }

    public void checkIsLoggedInFor(String tenantId) {
        requireNonBlank(tenantId, "TenantId must not be blank.");

        internalCheckLoggedIn();
        internalCheckTenantFor(tenantId);
    }

    public boolean isTenantAdmin() {
        if (!internalIsLoggedIn()) {
            return false;
        }

        return internalIsTenantAdmin();
    }

    public void checkIsTenantAdmin() {
        internalCheckLoggedIn();
        internalCheckTenantAdmin();
    }


    public boolean isTenantAdminFor(String tenantId) {
        requireNonBlank(tenantId, "TenantId must not be blank.");

        if (!internalIsLoggedIn() || isWrongTenantFor(tenantId)) {
            return false;
        }

        return internalIsTenantAdmin();
    }


    public void checkIsTenantAdminFor(String tenantId) {
        requireNonBlank(tenantId, "TenantId must not be blank.");

        internalCheckLoggedIn();
        internalCheckTenantFor(tenantId);
        internalCheckTenantAdmin();
    }


    public boolean isTenantRoot() {
        if (!internalIsLoggedIn()) {
            return false;
        }

        return internalIsTenantRoot();
    }

    public void checkIsTenantRoot() {
        internalCheckLoggedIn();
        internalCheckTenantRoot();
    }


    public boolean isTenantRootFor(String tenantId) {
        requireNonBlank(tenantId, "TenantId must not be blank.");

        if (!internalIsLoggedIn() || isWrongTenantFor(tenantId)) {
            return false;
        }

        return internalIsTenantRoot();
    }

    public void checkIsTenantRootFor(String tenantId) {
        requireNonBlank(tenantId, "TenantId must not be blank.");

        internalCheckLoggedIn();
        internalCheckTenantFor(tenantId);
        internalCheckTenantRoot();
    }

    public boolean isHumanUser() {
        if (!internalIsLoggedIn()) {
            return false;
        }

        return internalIsHumanUser();
    }

    public void checkIsHumanUser() {
        internalCheckLoggedIn();
        internalCheckHumanUser();
    }

    public boolean isHumanUserFor(String tenantId) {
        requireNonBlank(tenantId, "TenantId must not be blank.");

        if (!internalIsLoggedIn() || isWrongTenantFor(tenantId)) {
            return false;
        }

        return internalIsHumanUser();
    }

    public void checkIsHumanUserFor(String tenantId) {
        requireNonBlank(tenantId, "TenantId must not be blank.");

        internalCheckLoggedIn();
        internalCheckTenantFor(tenantId);
        internalCheckHumanUser();
    }

    private boolean isWrongTenantFor(String tenantId) {
        return !Objects.equals(this.tenantId, tenantId);
    }

    private boolean isTenantFor(String tenantId) {
        return Objects.equals(this.tenantId, tenantId);
    }

    private boolean internalIsTenantRoot() {
        return role == TENANT_ADMIN || role == ROBOT;
    }

    private boolean internalIsTenantAdmin() {
        return role == TENANT_ADMIN;
    }

    private boolean internalIsHumanUser() {
        return role == TENANT_ADMIN || role == TENANT_MEMBER;
    }

    private boolean internalIsLoggedIn() {
        return isNotBlank(tenantId) && role != null;
    }

    private void internalCheckLoggedIn() {
        if (!internalIsLoggedIn()) {
            throw new RuntimeException();
        }
    }

    private void internalCheckTenantRoot() {
        if (!internalIsTenantRoot()) {
            throw new RuntimeException();
        }
    }

    private void internalCheckTenantAdmin() {
        if (!internalIsTenantAdmin()) {
            throw new RuntimeException();
        }
    }

    private void internalCheckHumanUser() {
        if (!internalIsHumanUser()) {
            throw new RuntimeException();
        }
    }

    private void internalCheckTenantFor(String tenantId) {
        if (isWrongTenantFor(tenantId)) {
            throw new RuntimeException();
        }
    }

    @Override
    public String toString() {
        return "User[" + this.memberId + ":" + this.tenantId + "]";
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public Role getRole() {
        return role;
    }
}
