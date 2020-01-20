package psk.projects.dating_portal.profil;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class UserProfilController {
    private final UserProfilService userProfilService;

    @GetMapping("/user/profil")
    public UserProfil findUserProfil(Principal principal) {
        return userProfilService.findProfil(principal);
    }
}
