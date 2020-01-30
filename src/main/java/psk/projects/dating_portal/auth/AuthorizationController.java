package psk.projects.dating_portal.auth;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Builder
@Value
class UserInfo {
    Long userId;
    String login;
}

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:4200")
public class AuthorizationController {

    private final CreateUserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public void register(@RequestBody AppUser user) {
        userService.createUser(user);
    }

    @GetMapping("/user-info")
    public UserInfo getUserInfo(Principal principal) {
        AppUser user = userRepository.findByLogin(principal.getName());

        return UserInfo.builder()
                .userId(user.getId())
                .login(user.getLogin()).build();
    }
}
