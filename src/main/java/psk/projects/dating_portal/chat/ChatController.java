package psk.projects.dating_portal.chat;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/send/message")
    @SendTo("/topic/refresh/messages")
    public String onReceivedMessage(NewMessage message) {
        chatService.addNewMessage(message);
        return "Gonna be ok!";
    }
}
