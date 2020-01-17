package psk.projects.dating_portal.auth;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Integer> {
    long countByLogin(String login);
    boolean existsByLogin(String login);
}
