package psk.projects.dating_portal.tags;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"type", "tagId", "userId"}))
public class UserTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    Long tagId;

    @Column(nullable = false)
    Long userId;
    boolean checked;
    @Enumerated
    @Column(nullable = false)
    TagType type;

    @Enumerated
    @Column(nullable = false)
    TagPriority priority;
}

