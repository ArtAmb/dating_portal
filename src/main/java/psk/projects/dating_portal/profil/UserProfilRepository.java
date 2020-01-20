package psk.projects.dating_portal.profil;


import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserProfilRepository extends CrudRepository<UserProfil,Long>{
    UserProfil findByUserId(long userId);
}
