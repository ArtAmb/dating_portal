package psk.projects.dating_portal.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    Long userId;
    LocalDateTime dateTime;
    @Lob
    @Column(columnDefinition="CLOB")
    String message;

    @Column(nullable = false)
    Long chatId;
}
