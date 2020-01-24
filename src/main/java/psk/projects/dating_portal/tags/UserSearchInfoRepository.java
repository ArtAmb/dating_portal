package psk.projects.dating_portal.tags;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserSearchInfoRepository extends CrudRepository<UserSearchInfo, Long> {
    Optional<UserSearchInfo> findByUserId(long userId);
}
