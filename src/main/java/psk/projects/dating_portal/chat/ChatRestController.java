package psk.projects.dating_portal.chat;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class ChatRestController {
    private final ChatService chatService;

    @PostMapping("add-message")
    public void addMessage(@RequestBody AddMessageCommand command) {
        chatService.addChat(command);
    }

    @GetMapping("get-all-chats")
    public List<ChatViewDTO> getAllChats(Principal principal) {
        return chatService.getAllChats(principal);
    }

}
