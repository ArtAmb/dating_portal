package psk.projects.dating_portal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import psk.projects.dating_portal.auth.*;
import psk.projects.dating_portal.profil.UserProfil;
import psk.projects.dating_portal.profil.UserProfilRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final UserProfilRepository userProfilRepo;
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
        AppUser appUser = AppUser.builder().login("admin").email("admin@test.pl")
                .password(encoder.encode("test")).roles(developerRoles).build();
        userRepo.save(appUser);

        userProfilRepo.save(UserProfil.builder().userId(appUser.getId()).displayLogin("admin").description("to jest admni").build());

        AppUser normalUser = AppUser.builder().login("normal").email("normal@test.pl")
                .password(encoder.encode("test")).roles(Collections.singletonList(Roles.ROLE_NORMAL.toRole())).build();

        userRepo.save(normalUser);

        userProfilRepo.save(UserProfil.builder().userId(normalUser.getId()).displayLogin("normal").description("to jest normal").build());




    }


}
