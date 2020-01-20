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

    private final CreateUserService userService;

    @PostMapping("/register")
    public void register(@RequestBody AppUser user) {
       userService.createUser(user);
    }
}
