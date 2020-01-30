package psk.projects.dating_portal.tags;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import psk.projects.dating_portal.auth.UserRepository;
import psk.projects.dating_portal.profil.*;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserSearchService {
    private final UserRepository userRepository;
    private final UserTagRepository userTagRepository;
    private final UserSearchInfoRepository userSearchInfoRepository;
    private final UserProfilRepository userProfilRepository;
    private final TagRepository tagRepository;

    /**
     * algorytm wyszukania uzytkownikow po tagach, regionie i plci
     * Dane so pobierane po lognie zalogoanego uzytkownika
     * @param principal
     * @return 10 potencjalnych partnerow posiadajaych najwyzszy wspolczynnik dopasowania
     */
    public List<PotentialPartnerView> matchUsersForUser(Principal principal) {
        long userId = userRepository.findByLogin(principal.getName()).getId();
        UserSearchInfo conf = userSearchInfoRepository.findByUserId(userId).get();
        Map<Long, Tag> tagConfByIdMap = this.findTagsConfMap();
        List<UserTag> myPreferencesTag = findMyPartnerPrefeferences(userId, conf);

        List<PotentialPartner> potentialPartners = myPreferencesTag.stream()
                .flatMap(tag -> userTagRepository.findByTagIdAndTypeAndCheckedIsTrue(tag.getTagId(), TagType.MY).stream())
                .collect(Collectors.groupingBy(UserTag::getUserId))
                .entrySet().stream()
                .map(es -> new PotentialPartner(es.getKey(), es.getValue()))
                .peek(pp -> pp.calculateMatchingRate(myPreferencesTag))
                .collect(Collectors.toList());

        return potentialPartners.stream()
                .filter(pp -> {
                    if (pp.getUserId() == userId) return false;
                    UserProfil profil = userProfilRepository.findByUserId(pp.getUserId());

                    if (conf.preferredGender != GENDER.Default && conf.preferredGender != profil.getGender())
                        return false;

                    if (conf.preferredRegion != REGION.Default && conf.preferredRegion != profil.getRegion())
                        return false;

                    if (conf.preferredEyeColor != EYE_COLOR.Default && conf.preferredEyeColor != profil.getEyeColor())
                        return false;

                    if (conf.preferredHairColor != HAIR_COLOR.Default && conf.preferredHairColor != profil.getHairColor())
                        return false;

                    return true;
                })
                .sorted(Comparator.comparingDouble(PotentialPartner::getMatchingRate).reversed())
                .limit(10)
                .map(pp -> {
                    UserProfil profil = userProfilRepository.findByUserId(pp.getUserId());
/*
                    if(conf.preferredGender!=GENDER.Default && conf.preferredGender!=profil.getGender())
                        return null;

                    if(conf.preferredRegion!= REGION.Default && conf.preferredRegion!=profil.getRegion())
                        return null;
*/
                    return PotentialPartnerView.builder()
                            .userId(pp.getUserId())
                            .imageAvatarId(profil.getAvatarImageId())
                            .description(profil.getDescription())
                            .login(profil.getDisplayLogin())
                            .tags(pp.getUserTags().stream().map(tag -> {
                                return PartnerTag.builder()
                                        .name(tagConfByIdMap.get(tag.getTagId()).getName())
                                        .priority(tag.getPriority()).build();
                            }).collect(Collectors.toList()))
                            .matchingRate(pp.getMatchingRate())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private Map<Long, Tag> findTagsConfMap() {
        return tagRepository.findAll().stream().collect(Collectors.toMap(Tag::getId, t -> t));
    }

    private List<UserTag> findMyPartnerPrefeferences(long userId, UserSearchInfo conf) {
        switch (conf.getAlgorithmType()) {

            case LOOKING_FOR_MYSELF:
                return userTagRepository.findByUserIdAndType(userId, TagType.MY);
            case LOOKING_FOR_MY_PREFERENCES:
                return userTagRepository.findByUserIdAndType(userId, TagType.PARTNER);
        }

        throw new IllegalStateException("Algorithm " + conf.getAlgorithmType().name() + " is not handled!");
    }

    /**
     * aktualizacja preferecji do partnera
     * @param dto
     * @param principal
     */
    public void updateAdditionalSearchInfo(UpdateSearchInfoDTO dto, Principal principal) {
        long userId = userRepository.findByLogin(principal.getName()).getId();
        UserSearchInfo conf = userSearchInfoRepository.findByUserId(userId).get();
        conf.setPreferredGender(dto.gender);
        conf.setPreferredRegion(dto.region);
        conf.setPreferredEyeColor(dto.eyeColor);
        conf.setPreferredHairColor(dto.hairColor);


        userSearchInfoRepository.save(conf);

        conf = userSearchInfoRepository.findByUserId(userId).get();

        System.out.println("plec: " + conf.preferredGender + " region: " + conf.preferredRegion+" eye: " + conf.preferredEyeColor + " hair: " + conf.preferredHairColor);
    }
}
