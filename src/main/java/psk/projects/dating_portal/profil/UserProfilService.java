package psk.projects.dating_portal.profil;

import lombok.AllArgsConstructor;
import org.hibernate.cache.spi.support.CollectionNonStrictReadWriteAccess;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import psk.projects.dating_portal.auth.AppUser;
import psk.projects.dating_portal.auth.UserRepository;

import java.io.Console;
import java.util.List;

@Service
@AllArgsConstructor
public class UserProfilService {
    private final UserRepository userRepository;
    private final UserProfilRepository userProfilRepository;

    public UserProfil findByUser(String userLogin) {
        //System.out.println("call find by user " + userLogin);
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

    public void updateUserProfil(UserProfil updatedUserProfil)
    {
        UserProfil profil = userProfilRepository.findByUserId(updatedUserProfil.getUserId());
        profil.setDisplayLogin(updatedUserProfil.getDisplayLogin());
        profil.setDescription(updatedUserProfil.getDescription());

        userProfilRepository.save(profil);

    }
}
