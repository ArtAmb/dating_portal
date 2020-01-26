package psk.projects.dating_portal.auth;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Long> {
    long countByLogin(String login);
    boolean existsByLogin(String login);
    AppUser findByLogin(String login);
}
