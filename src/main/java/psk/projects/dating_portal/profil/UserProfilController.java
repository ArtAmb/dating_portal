package psk.projects.dating_portal.profil;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
@AllArgsConstructor
public class UserProfilController {

    private final UserProfilService userProfilService;

    @GetMapping("user/profil-data")
    public UserProfil findProfil(Principal principal) {
        return userProfilService.findByUser(principal.getName());
    }

    @PostMapping("user/profil-update")
    public void updateProfil(@RequestBody UserProfil userProfil){ userProfilService.updateUserProfil(userProfil);}


    @GetMapping("/user/profil-view")
    public UserProfil findUserProfil(@RequestParam("userId") String userId) {
        return userProfilService.findProfil(Long.parseLong(userId));
    }

    @GetMapping("/user/profil-all")
    public Collection<UserProfil> findAllProfils(){return userProfilService.getAllProfils();}

}
