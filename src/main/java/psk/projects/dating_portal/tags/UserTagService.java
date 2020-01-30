package psk.projects.dating_portal.tags;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import psk.projects.dating_portal.auth.UserRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserTagService {
    private final UserRepository userRepository;
    private final UserTagRepository userTagRepository;
    private final UserSearchInfoRepository userSearchInfoRepository;

    /**
     * metoda do aktualizacji tagow
     * @param dto
     * @param context
     * @param principal
     * @return
     */
    @Transactional
    public UserTag changeUserTags(@RequestBody UpdateUserTagDTO dto, @PathVariable TagType context, Principal principal) {
        long userId = userRepository.findByLogin(principal.getName()).getId();
        UserTag tag = userTagRepository.findByTagIdAndUserIdAndType(dto.getTagId(), userId, context);
        if (tag == null) {
            tag = UserTag.builder()
                    .userId(userId)
                    .tagId(dto.getTagId())
                    .checked(dto.isChecked())
                    .priority(dto.getTagPriority())
                    .type(context)
                    .build();
        }

        tag.setChecked(dto.isChecked());
        tag.setPriority(dto.getTagPriority());
        userTagRepository.save(tag);

        UserSearchInfo userSearchInfo = findSearchInfByUserId(userId);
        List<UserTag> allUserTags = userTagRepository.findByUserIdAndType(userId, TagType.MY);

        String tagsInfo = allUserTags.stream()
                .map(UserTag::getTagId)
                .sorted()
                .map(Object::toString)
                .collect(Collectors.joining(" "));

        userSearchInfo.setTagsInfo(tagsInfo);
        userSearchInfoRepository.save(userSearchInfo);

        return tag;
    }

    private UserSearchInfo findSearchInfByUserId(long userId) {
        Optional<UserSearchInfo> searchInfo = userSearchInfoRepository.findByUserId(userId);

        return searchInfo.orElseGet(() -> userSearchInfoRepository.save(UserSearchInfo.builder()
                .algorithmType(PartnerSearchAlgorithm.LOOKING_FOR_MYSELF)
                .userId(userId)
                .tagsInfo("")
                .build()));
    }
}
