package psk.projects.dating_portal.chat;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("new-message")
    public Message addNewMessage(@RequestBody NewMessage command) {
        return chatService.addNewMessage(command);
    }

    @GetMapping("get-all-chats")
    public List<ChatViewDTO> getAllChats(Principal principal) {
        return chatService.getAllChats(principal);
    }


    @GetMapping("chat/{chatId}/read-msg-count/{msgCount}/limit/{limit}")
    public List<Message> getChatMessages(@PathVariable Long chatId, @PathVariable Integer msgCount, @PathVariable Integer limit) {
        return chatService.getChat(chatId, msgCount, limit);
    }

}
