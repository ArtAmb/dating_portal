package psk.projects.dating_portal.profil;

import org.springframework.data.repository.CrudRepository;

public interface UserProfilRepo extends CrudRepository<UserProfil, Long> {
    UserProfil findByUserId(Long id);
}
