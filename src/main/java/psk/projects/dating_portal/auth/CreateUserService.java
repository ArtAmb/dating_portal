package psk.projects.dating_portal.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import psk.projects.dating_portal.profil.*;
import psk.projects.dating_portal.tags.PartnerSearchAlgorithm;
import psk.projects.dating_portal.tags.UserSearchInfo;
import psk.projects.dating_portal.tags.UserSearchInfoRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CreateUserService {
    private final UserProfilRepository userProfilRepo;
    private final UserRepository userRepository;
    private final UserSearchInfoRepository userSearchInfoRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @Transactional
    public Long createUser(AppUser user) {
        return createUser(user, Collections.singletonList(Roles.ROLE_NORMAL.toRole()));
    }

    @Transactional
    public Long createAdmin(AppUser user) {
        List<Role> allRoles = Stream.of(Roles.values()).map(Roles::toRole).collect(Collectors.toList());
        return createUser(user, allRoles);
    }

    public Long createUser(AppUser user, List<Role> roles) {
        Assert.notNull(user.getLogin(), "login is required");
        Assert.notNull(user.getPassword(), "password is required");
        Assert.notNull(user.getEmail(), "email is required");

        long count = userRepository.countByLogin(user.getLogin());

        if (count > 0)
            throw new IllegalStateException("There is user with login == " + user.getLogin());

        String rawPass = user.getPassword();
        user.setPassword(encoder.encode(rawPass));
        user.setRoles(roles);
        Long userId = userRepository.save(user).getId();

        UserProfil userProfil = UserProfil.builder()
                .userId(userId)
                .displayLogin(user.getLogin())
                .description("Nowy użytkownik.")
                .birthDate(new Date())
                .gender(GENDER.Default)
                .hairColor(HAIR_COLOR.Default)
                .region(REGION.Default)
                .eyeColor(EYE_COLOR.Default)
                .height(175)
                .weight(60)
                .build();

        userProfilRepo.save(userProfil);
        UserSearchInfo searchConfig = UserSearchInfo.builder()
                .userId(userId)
                .algorithmType(PartnerSearchAlgorithm.LOOKING_FOR_MYSELF)
                .preferredGender(GENDER.Default)
                .preferredRegion(REGION.Default)
                .preferredEyeColor(EYE_COLOR.Default)
                .preferredHairColor(HAIR_COLOR.Default)
                .tagsInfo("").build();

        userSearchInfoRepository.save(searchConfig);
        return userId;
    }

}
