package psk.projects.dating_portal.auth;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:4200")
public class AuthorizationController {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public void register(@RequestBody AppUser user) {
        Assert.notNull(user.getLogin(), "login is required");
        Assert.notNull(user.getPassword(), "password is required");
        Assert.notNull(user.getEmail(), "email is required");

        long count = userRepository.countByLogin(user.getLogin());

        if (count > 0)
            throw new IllegalStateException("There is user with login == " + user.getLogin());

        String rawPass = user.getPassword();
        user.setPassword(encoder.encode(rawPass));
        user.setRoles(Collections.singletonList(Roles.ROLE_NORMAL.toRole()));
        userRepository.save(user);
    }
}
