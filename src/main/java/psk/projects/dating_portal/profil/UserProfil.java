package psk.projects.dating_portal.profil;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class UserProfil {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String displayLogin;

//    private List<Gallery> galleries;

}
