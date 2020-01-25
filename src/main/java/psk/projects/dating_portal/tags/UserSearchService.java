package psk.projects.dating_portal.tags;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import psk.projects.dating_portal.auth.UserRepository;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserSearchService {
    private final UserRepository userRepository;
    private final UserTagRepository userTagRepository;
    private final UserSearchInfoRepository userSearchInfoRepository;


    public List<PotentialPartner> matchUsersForUser(Principal principal) {
        long userId = userRepository.findByLogin(principal.getName()).getId();
        UserSearchInfo conf = userSearchInfoRepository.findByUserId(userId).get();

        List<UserTag> myPreferencesTag = findMyPartnerPrefeferences(userId, conf);

        List<PotentialPartner> potentialPartners = myPreferencesTag.stream()
                .flatMap(tag -> userTagRepository.findByTagIdAndTypeAndCheckedIsTrue(tag.getTagId(), TagType.MY).stream())
                .collect(Collectors.groupingBy(UserTag::getUserId))
                .entrySet().stream()
                .map(es -> new PotentialPartner(es.getKey(), es.getValue()))
                .collect(Collectors.toList());

        return potentialPartners.stream()
                .sorted(Comparator.comparingInt(PotentialPartner::getMatchingRate).reversed())
                .limit(10)
                .collect(Collectors.toList());
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
}
