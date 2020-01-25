package psk.projects.dating_portal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import psk.projects.dating_portal.auth.*;
import psk.projects.dating_portal.tags.CategoryTestService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final RoleRepository roleRepo;
    private final CreateUserService createUserService;
    private final CategoryTestService categoryTestService;
    private final UserRepository userRepo;


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


        initSystem();
    }

    @Transactional
    private void initSystem() {
        categoryTestService.deleteAll();
        categoryTestService.initTags();


        List<Long> ids = new LinkedList<>();
        for (int i = 0; i < 100; ++i) {
            String login = "user" + i;
            Long id = createUserService.createUser(AppUser.builder().login(login).email(login + "@test.pl")
                    .password("test").build());

            ids.add(id);
            System.out.println("user added: " + login);
        }

        categoryTestService.initUserTags(ids);
    }


}
