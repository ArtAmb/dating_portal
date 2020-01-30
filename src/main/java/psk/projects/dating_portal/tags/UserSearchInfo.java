package psk.projects.dating_portal.tags;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import psk.projects.dating_portal.profil.EYE_COLOR;
import psk.projects.dating_portal.profil.GENDER;
import psk.projects.dating_portal.profil.HAIR_COLOR;
import psk.projects.dating_portal.profil.REGION;

import javax.persistence.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Enumerated
    @Column(nullable = false)
    PartnerSearchAlgorithm algorithmType;

    String tagsInfo;

    @Enumerated
    @Column(nullable = false)
    GENDER preferredGender;

    @Enumerated
    @Column(nullable = false)
    REGION preferredRegion;

    @Enumerated
    @Column(nullable = false)
    EYE_COLOR preferredEyeColor;

    @Enumerated
    @Column(nullable = false)
    HAIR_COLOR preferredHairColor;
}


