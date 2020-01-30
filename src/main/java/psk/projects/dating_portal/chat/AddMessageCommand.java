package psk.projects.dating_portal.chat;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddMessageCommand {
    private Set<Long> usersIds;
    private Long chatId;
    private Long userId;

    private String message;
}
