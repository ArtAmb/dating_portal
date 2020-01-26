package psk.projects.dating_portal.tags;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagCategoryRepository extends CrudRepository<TagCategory, Long> {
    List<TagCategory> findAll();
}

interface TagRepository extends CrudRepository<Tag, Long> {
    List<Tag> findAll();
}

interface UserTagRepository extends CrudRepository<UserTag, Long> {
    UserTag findByTagIdAndUserIdAndType(Long tagId, Long userId, TagType type);

    List<UserTag> findByUserIdAndType(Long userId, TagType type);

    List<UserTag> findByUserId(Long userId);

    List<UserTag> findByTagIdAndType(Long tagId, TagType type);
    List<UserTag> findByTagIdAndTypeAndCheckedIsTrue(Long tagId, TagType type);
}