package psk.projects.dating_portal.tags;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import psk.projects.dating_portal.auth.UserRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
class TagDTO {
    Long tagId;
    Long categoryId;
    String name;
}

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
class TagCategoryDTO {
    Long tagCategoryId;
    String name;
}

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
class UpdateUserTagDTO {
    Long tagId;
    boolean checked;
    TagPriority tagPriority;
}

@RestController
@AllArgsConstructor
public class TagsController {
    private final TagCategoryRepository tagCategoryRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final UserTagRepository userTagRepository;
    private final UserTagService userTagService;
    private final UserSearchService userSearchService;

    @GetMapping("/all-tags")
    public List<TagCategory> findAllTags() {
        return tagCategoryRepository.findAll();
    }

    @PostMapping("/update-tag")
    public void updateTag(@RequestBody TagDTO tagDTO) {
        Tag currTag = tagRepository.findById(tagDTO.getTagId()).get();
        currTag.setName(tagDTO.getName());
        tagRepository.save(currTag);
    }

    @PostMapping("/add-new-tag")
    @Transactional
    public Tag addTag(@RequestBody TagDTO tagDTO) {
        Assert.notNull(tagDTO.getCategoryId(), "CategoryId is required");
        Tag tmp = Tag.builder().archived(false).name(tagDTO.getName()).build();
        tmp = tagRepository.save(tmp);
        TagCategory category = tagCategoryRepository.findById(tagDTO.getCategoryId()).get();


        List<Tag> tags = category.getTags();
        if (tags == null) {
            category.setTags(new LinkedList<>());
        }

        category.getTags().add(tmp);

        tagCategoryRepository.save(category);

        return tmp;
    }

    @PostMapping("/add-new-tag-category")
    public TagCategory addTagCategory(@RequestBody TagCategoryDTO tagDTO) {
        TagCategory tmp = TagCategory.builder().archived(false).name(tagDTO.getName()).build();
        return tagCategoryRepository.save(tmp);
    }

    @PostMapping("/update-tag-category")
    public void updateTagCategory(@RequestBody TagCategoryDTO tagDTO) {
        TagCategory currTag = tagCategoryRepository.findById(tagDTO.getTagCategoryId()).get();
        currTag.setName(tagDTO.getName());
        tagCategoryRepository.save(currTag);
    }

    @GetMapping("/all-user-tags")
    public List<UserTag> getAllUserTags(Principal principal) {
        Long userId = userRepository.findByLogin(principal.getName()).getId();
        return userTagRepository.findByUserId(userId);
    }

    @PostMapping("/update-user-tag/context/{context}")
    public UserTag changeUserTags(@RequestBody UpdateUserTagDTO dto, @PathVariable TagType context, Principal principal) {
        return userTagService.changeUserTags(dto, context, principal);
    }

    @GetMapping("/match-partner")
    public List<PotentialPartnerView> matchPartners(Principal principal) {
        return userSearchService.matchUsersForUser(principal);
    }

}
