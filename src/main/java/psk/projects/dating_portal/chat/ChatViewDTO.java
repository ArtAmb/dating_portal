package psk.projects.dating_portal.chat;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ChatViewDTO {
    Long chatId;
    Message lastMessage;

    Long myUserId;

    Long recipientUserId;
    String recipientLogin;
    String recipientChatLogin;
}
