package psk.projects.dating_portal.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    Long userId;

    @Enumerated
    @Column(nullable = false)
    NotificationType type;

    @Enumerated
    @Column(nullable = false)
    NotificationState state;

    @Column(nullable = false)
    Long triggerUserId;
}
