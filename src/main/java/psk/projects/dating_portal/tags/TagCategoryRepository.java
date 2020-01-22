package psk.projects.dating_portal.tags;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagCategoryRepository extends CrudRepository<TagCategory, Long> {
    List<TagCategory> findAll();
}

interface TagRepository extends CrudRepository<Tag, Long> {
}