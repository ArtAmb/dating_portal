package psk.projects.dating_portal.tags;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import psk.projects.dating_portal.profil.GENDER;
import psk.projects.dating_portal.profil.REGION;

import java.security.Principal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
class UpdateSearchInfoDTO {
    GENDER gender;
    REGION region;
}

@RestController
@AllArgsConstructor
public class ProfilFiltersController {

    private final UserSearchService userSearchService;

    @PostMapping("update-search-info")
    public void updateSearchInfo(@RequestBody UpdateSearchInfoDTO dto, Principal principal)
    {
        userSearchService.updateAdditionalSearchInfo(dto,principal);
        return;
    }
}
