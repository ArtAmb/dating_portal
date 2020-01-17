package psk.projects.dating_portal.auth;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Roles {
    ROLE_ADMIN(Consts.ROLE_ADMIN), ROLE_NORMAL(Consts.ROLE_NORMAL);

    private final String name;
    private Role role;

    public final String getRoleName() {
        return name;
    }

    Roles(String name) {
        this.name = name;
        role = new Role(name);
    }

    public Role toRole() {
        return role;
    }

    public static List<Role> toRoleValues() {
        return Arrays.stream(values()).map(Roles::toRole).collect(Collectors.toList());
    }

    public class Consts {
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_NORMAL = "ROLE_NORMAL";
    }
}