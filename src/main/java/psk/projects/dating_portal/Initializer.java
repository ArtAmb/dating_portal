package psk.projects.dating_portal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import psk.projects.dating_portal.auth.*;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostConstruct
    void initApplication() {
        addSystemUsers();
    }

    private void addSystemUsers() {
        for (Role role : Roles.toRoleValues()) {
            roleRepo.save(role);
        }

        List<Role> developerRoles = new LinkedList<>();
        developerRoles.add(Roles.ROLE_ADMIN.toRole());
        developerRoles.add(Roles.ROLE_NORMAL.toRole());
        userRepo.deleteAll();

        userRepo.save(AppUser.builder().login("admin").email("admin@test.pl")
                .password(encoder.encode("test")).roles(developerRoles).build());

        userRepo.save(AppUser.builder().login("normal").email("normal@test.pl")
                .password(encoder.encode("test")).roles(Collections.singletonList(Roles.ROLE_NORMAL.toRole())).build());


    }


}
