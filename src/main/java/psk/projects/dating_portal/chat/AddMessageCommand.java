package psk.projects.dating_portal.chat;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class AddMessageCommand {
    private Set<Long> usersIds;
    private Long chatId;
    private Long userId;

    private String message;
}
