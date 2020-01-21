package psk.projects.dating_portal.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import psk.projects.dating_portal.profil.UserProfil;
import psk.projects.dating_portal.profil.UserProfilRepo;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CreateUserService {
    private final UserProfilRepo userProfilRepo;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @Transactional
    public void createUser(AppUser user) {
        createUser(user, Collections.singletonList(Roles.ROLE_NORMAL.toRole()));
    }

    @Transactional
    public void createAdmin(AppUser user) {
        List<Role> allRoles = Stream.of(Roles.values()).map(Roles::toRole).collect(Collectors.toList());
        createUser(user, allRoles);
    }

    public void createUser(AppUser user, List<Role> roles) {
        Assert.notNull(user.getLogin(), "login is required");
        Assert.notNull(user.getPassword(), "password is required");
        Assert.notNull(user.getEmail(), "email is required");

        long count = userRepository.countByLogin(user.getLogin());

        if (count > 0)
            throw new IllegalStateException("There is user with login == " + user.getLogin());

        String rawPass = user.getPassword();
        user.setPassword(encoder.encode(rawPass));
        user.setRoles(roles);
        Long userId = userRepository.save(user).getId();

        UserProfil userProfil = UserProfil.builder()
                .userId(userId)
                .displayLogin(user.getLogin())
                .description("to jest nowy użytkownik")
                .build();

        userProfilRepo.save(userProfil);
    }

}
