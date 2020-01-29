package psk.projects.dating_portal.chat;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate template;

    @MessageMapping("/send/message")
    @SendTo("/topic/refresh/messages")
    public String onReceivedMessage(String message) {
        return "Gonna be okey!";
    }
}
