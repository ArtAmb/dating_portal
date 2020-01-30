package psk.projects.dating_portal.chat;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final RegisterBrokerService registerBrokerService;

    /**
     * Wukorzystanie websocketow do chat'u
     */
    @MessageMapping("/send/message")
    @SendTo("/topic/refresh/messages")
    public NotificationWithMessage onReceivedMessage(NewMessage message) throws InterruptedException {
        return registerBrokerService.subscribe(message.getUserId());
    }
}
