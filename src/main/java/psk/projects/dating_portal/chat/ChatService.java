package psk.projects.dating_portal.chat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import psk.projects.dating_portal.auth.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatContributorRepository chatContributorRepository;
    private final MessageRepository messageRepository;


    @Transactional
    public void addChat(AddMessageCommand command) {
        Chat chat = findChat(command);
        Message msg = Message.builder()
                .userId(command.getUserId())
                .message(command.getMessage())
                .dateTime(LocalDateTime.now())
                .chatId(chat.getId())
                .build();

        messageRepository.save(msg);
    }

    private Chat findChat(AddMessageCommand command) {
        String query = command.getUsersIds().stream()
                .sorted()
                .map(Object::toString).collect(Collectors.joining(" "));

        List<Chat> chats = chatRepository.findBySearchInfo(query);

        if (chats.size() > 1) {
            throw new IllegalStateException("Too much chats for " + query);
        }

        if (chats.size() == 0) {
            List<ChatContributor> contributors = command.getUsersIds().stream()
                    .map(userId ->
                            ChatContributor.builder()
                                    .userId(userId)
                                    .build()).collect(Collectors.toList());


            Chat tmp = Chat.builder()
                    .contributors(contributors)
                    .searchInfo(query)
                    .build();

            return chatRepository.save(tmp);
        }

        return chats.get(0);
    }
}
