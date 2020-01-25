package psk.projects.dating_portal.tags;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryTestService {
    private final TagCategoryRepository tagCategoryRepository;
    private final TagRepository tagRepository;
    private final UserTagRepository userTagRepository;


    public void deleteAll() {
        tagCategoryRepository.deleteAll();
        userTagRepository.deleteAll();
        tagRepository.deleteAll();
    }

    public void initTags() {
        for (int i = 0; i < 10; ++i) {
            String categoryName = "K" + (i + 1);
            TagCategory tmp = TagCategory.builder().archived(false).name(categoryName).build();
            tagCategoryRepository.save(tmp);


            ArrayList<Tag> tags = new ArrayList<>();
            for (int j = 0; j < 10; ++j) {
                String tagName = categoryName + "Tag" + (j + 1);
                Tag tag = tagRepository.save(Tag.builder().name(tagName).archived(false).build());
                tags.add(tag);
            }

            tmp.setTags(tags);
            tagCategoryRepository.save(tmp);
        }
    }

    public void initUserTags(List<Long> usersIds) {
        List<TagCategory> allTagsCategories = tagCategoryRepository.findAll();

        usersIds.forEach(userId -> {
            allTagsCategories.forEach(tagCategory -> {
                tagCategory.getTags().stream().limit(5).forEach(tag ->
                        userTagRepository.save(UserTag.builder()
                                .type(TagType.MY)
                                .priority(TagPriority.NORMAL)
                                .userId(userId)
                                .checked(true)
                                .tagId(tag.getId()).build())
                );
            });
        });
    }

}
