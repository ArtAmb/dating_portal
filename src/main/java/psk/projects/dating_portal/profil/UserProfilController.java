package psk.projects.dating_portal.profil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;
import psk.projects.dating_portal.auth.UserRepository;
import psk.projects.dating_portal.tags.PartnerSearchAlgorithm;
import psk.projects.dating_portal.tags.UserSearchInfo;
import psk.projects.dating_portal.tags.UserSearchInfoRepository;

import java.security.Principal;
import java.util.Collection;

@RestController
@AllArgsConstructor
public class UserProfilController {

    private final UserRepository userRepository;
    private final UserProfilService userProfilService;
    private final UserSearchInfoRepository userSearchInfoRepository;

    @GetMapping("user/profil-data")
    public UserProfil findProfil(Principal principal) {
        return userProfilService.findByUser(principal.getName());
    }

    @PostMapping("user/profil-update")
    public void updateProfil(@RequestBody UserProfil userProfil) {
        userProfilService.updateUserProfil(userProfil);
    }


    @GetMapping("/user/profil")
    public UserProfil findLoggedUserProfil(Principal principal) {
        return userProfilService.findProfil(principal);
    }

    @GetMapping("/user/profil-view")
    public UserProfil findUserProfil(@RequestParam("userId") String userId) {
        return userProfilService.findProfil(Long.parseLong(userId));
    }

    @GetMapping("/user/profil-all")
    public Collection<UserProfil> findAllProfils() {
        return userProfilService.getAllProfils();
    }

    @GetMapping("/user/search-info")
    public UserSearchInfo getUserSearchInfo(Principal principal) {
        Long userId = userRepository.findByLogin(principal.getName()).getId();
        return userSearchInfoRepository.findByUserId(userId).get();
    }

    @PostMapping("/update-search-algorithm")
    public void updateSearchAlgorithm(@RequestBody UpdateSearchAlgorithmCommand command, Principal principal) {
        Long userId = userRepository.findByLogin(principal.getName()).getId();
        UserSearchInfo info = userSearchInfoRepository.findByUserId(userId).get();
        info.setAlgorithmType(command.getPartnerSearchAlgorithm());
        userSearchInfoRepository.save(info);
    }

}

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
class UpdateSearchAlgorithmCommand {
    PartnerSearchAlgorithm partnerSearchAlgorithm;
}