package psk.projects.dating_portal.profil;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import psk.projects.dating_portal.auth.AppUser;
import psk.projects.dating_portal.auth.UserRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;

@Service
@AllArgsConstructor
public class UserProfilService {
    private final UserProfilRepo userProfilRepo;
    private final UserRepository userRepo;
    private final ImageRepository imageRepository;


    public UserProfil findProfil(Principal principal) {
        long userId = findUserByLogin(principal.getName()).getId();
        UserProfil profil = userProfilRepo.findByUserId(userId);

        return profil;
    }

    @Transactional
    public ImageInfo publishAvatar(MultipartFile file, Principal principal) throws IOException {
        Image image = Image.builder()
                .binaryImage(file.getBytes())
                .build();

        Long imageId = imageRepository.save(image).getId();

        AppUser user = findUserByLogin(principal.getName());

        UserProfil userProfil = userProfilRepo.findByUserId(user.getId());
        userProfil.setAvatarImageId(imageId);
        userProfilRepo.save(userProfil);

        return ImageInfo.builder()
                .imageId(imageId)
                .description("")
                .build();
    }

    private AppUser findUserByLogin(String userLogin) {
        Assert.notNull(userLogin, "userLogin is required");
        AppUser user = userRepo.findByLogin(userLogin);
        if (user == null)
            throw new IllegalStateException("User not found for login " + userLogin);
        return user;
    }
}
