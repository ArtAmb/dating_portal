package psk.projects.dating_portal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import psk.projects.dating_portal.auth.*;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final RoleRepository roleRepo;
    private final CreateUserService createUserService;
    private final UserRepository userRepo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostConstruct
    void initApplication() {
        addSystemUsers();
    }

    private void addSystemUsers() {
        for (Role role : Roles.toRoleValues()) {
            roleRepo.save(role);
        }

        userRepo.deleteAll();
        createUserService.createAdmin(AppUser.builder().login("admin").email("admin@test.pl")
                .password("test").build());

        createUserService.createUser(AppUser.builder().login("normal").email("normal@test.pl")
                .password("test").build());
    }


}
