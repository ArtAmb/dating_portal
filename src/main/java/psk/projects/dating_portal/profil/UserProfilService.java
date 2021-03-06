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
import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class UserProfilService {
    private final UserRepository userRepository;
    private final UserProfilRepository userProfilRepository;
    private final ImageRepository imageRepository;

    /**
     * wyszukanie prodilu uzytkownika po loginie
     * @param userLogin
     * @return
     */
    public UserProfil findByUser(String userLogin) {
        AppUser user = findUserByLogin(userLogin);

        return userProfilRepository.findByUserId(user.getId());
    }

    private AppUser findUserByLogin(String userLogin) {
        Assert.notNull(userLogin, "userLogin is required");
        AppUser user = userRepository.findByLogin(userLogin);
        if (user == null)
            throw new IllegalStateException("User not found for login " + userLogin);
        return user;
    }

    /**
     * pobranie wszystkich profili
     * @return
     */
    public Collection<UserProfil> getAllProfils() {
        Collection<UserProfil> collection = new ArrayList<UserProfil>();

        userProfilRepository.findAll().iterator().forEachRemaining(collection::add);

        return collection;

    }

    /**
     * aktualizacja danych profilu
     * @param updatedUserProfil
     */
    public void updateUserProfil(UserProfil updatedUserProfil) {
        UserProfil profil = userProfilRepository.findByUserId(updatedUserProfil.getUserId());
        profil.setDisplayLogin(updatedUserProfil.getDisplayLogin());
        profil.setDescription(updatedUserProfil.getDescription());
        profil.setBirthDate(updatedUserProfil.getBirthDate());
        profil.setGender(updatedUserProfil.getGender());
        profil.setHeight(updatedUserProfil.getHeight());
        profil.setWeight(updatedUserProfil.getWeight());
        profil.setEyeColor(updatedUserProfil.getEyeColor());
        profil.setHairColor(updatedUserProfil.getHairColor());
        profil.setRegion(updatedUserProfil.getRegion());

        userProfilRepository.save(profil);
    }

    /**
     * dodanie zdjecia profilowego
     * @param file
     * @param principal
     * @return
     * @throws IOException
     */
    @Transactional
    public ImageInfo publishAvatar(MultipartFile file, Principal principal) throws IOException {
        Image image = Image.builder()
                .binaryImage(file.getBytes())
                .build();

        Long imageId = imageRepository.save(image).getId();

        AppUser user = findUserByLogin(principal.getName());

        UserProfil userProfil = userProfilRepository.findByUserId(user.getId());
        userProfil.setAvatarImageId(imageId);
        userProfilRepository.save(userProfil);

        return ImageInfo.builder()
                .imageId(imageId)
                .description("")
                .build();
    }

    /**
     * wyszukanie profilu zalogowane uzytkownika
     * @param principal
     * @return
     */
    public UserProfil findProfil(Principal principal) {
        long userId = userRepository.findByLogin(principal.getName()).getId();
        UserProfil profil = userProfilRepository.findByUserId(userId);
        return profil;
    }

    /**
     * wyszukanie profilu po id
     * @param userId
     * @return
     */
    public UserProfil findProfil(Long userId) {

        UserProfil profil = userProfilRepository.findByUserId(userId);

        return profil;
    }

}
