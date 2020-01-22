package psk.projects.dating_portal.profil;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;



@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfil {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long userId;
    private String displayLogin;

    private Long avatarImageId;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;


    private GENDER gender;

}

