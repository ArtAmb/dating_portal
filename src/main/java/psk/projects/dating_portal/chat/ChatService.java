package psk.projects.dating_portal.chat;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import psk.projects.dating_portal.auth.UserRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatContributorRepository chatContributorRepository;
    private final MessageRepository messageRepository;
    private final RegisterBrokerService registerBrokerService;


    /**
     * Pobranie wszystkich chatow zalogowane uzytkownika
     */
    public List<ChatViewDTO> getAllChats(Principal principal) {
        Long userId = userRepository.findByLogin(principal.getName()).getId();
        List<Chat> chats = chatRepository.findBySearchInfoLike("%" + userId + "%");

        return chats.stream().map(chat -> {
            ChatContributor recepient = chat.getContributors().stream()
                    .filter(tmp -> tmp.getUserId() != null)
                    .filter(tmp -> !tmp.getUserId().equals(userId))
                    .findFirst().get();

            List<Message> messages = findMessages(chat.getId(), 0);
            String recipientLogin = userRepository.findById(recepient.getUserId()).get().getLogin();


            Message lastMsg = messages.stream().findFirst().orElse(null);

            return ChatViewDTO.builder()
                    .chatId(chat.getId())
                    .lastMessage(lastMsg)
                    .myUserId(userId)
                    .recipientUserId(recepient.getUserId())
                    .recipientLogin(recipientLogin)
                    .recipientChatLogin(recepient.getChatNickname())
                    .build();
        }).collect(Collectors.toList());
    }

    private List<Message> findMessages(Long chatId, int page) {
        return messageRepository.findByChatId(chatId, PageRequest.of(page, 10, Sort.by("dateTime").descending()));
    }

    /**
     * dodanie nowej wiadomosci, jezeli chat nie istnieje jest on tworzony
     */
    @Transactional
    public void addChat(AddMessageCommand command) {
        Chat chat = findChat(command);
        NewMessage msg = NewMessage.builder()
                .userId(command.getUserId())
                .message(command.getMessage())
                .chatId(chat.getId())
                .build();

        addNewMessage(msg);
    }

    private Chat findChat(AddMessageCommand command) {
        if (command.getUsersIds().size() < 2)
            throw new IllegalStateException("Required at least 2 chat contributors");

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
                                    .build())
                    .map(contributor -> chatContributorRepository.save(contributor)).collect(Collectors.toList());


            Chat tmp = Chat.builder()
                    .contributors(contributors)
                    .searchInfo(query)
                    .build();

            return chatRepository.save(tmp);
        }

        return chats.get(0);
    }

    /**
     * dodanie nowej wiadomosci, wszyscy uztkownicy powiazani z chatem
     * otrzymuja notifikacje i za pomoca websocketow odswiezaja chat
     */
    public Message addNewMessage(NewMessage newMessage) {
        Message msg = Message.builder()
                .userId(newMessage.getUserId())
                .message(newMessage.getMessage())
                .dateTime(LocalDateTime.now())
                .chatId(newMessage.getChatId())
                .build();

        Message msgToPublish = messageRepository.save(msg);
        Chat chat = chatRepository.findById(newMessage.getChatId()).get();
        chat.getContributors()
                .stream()
                .filter(chatContributor -> chatContributor.getUserId() != null)
                .filter(chatContributor -> !chatContributor.getUserId().equals(newMessage.getUserId()))
                .forEach(chatContributor -> {
                    registerBrokerService.publish(Notification.builder()
                            .triggerUserId(newMessage.getUserId())
                            .type(NotificationType.NEW_MESSAGE)
                            .userId(chatContributor.getUserId()).build(), msgToPublish);
                });


        return msgToPublish;
    }

    /**
     * Pobranie wiadomosic z dla chatu - wykorzystywana w inicjajcji chatu lub pobieraniu danych histroycznych
     * @param chatId id chatu
     * @param msgCount wszystki pobrane na froncie wiadomosci
     * @param limit ile wiadomosci chcemy uzyskac
     * @return zostanie zwrocona lista wiadomosci posortowana po dacie wstawienia. lIczba elementow jest okreslona przez limit  i przesunienta o msgCount
     */
    public List<Message> getChat(Long chatId, Integer msgCount, Integer limit) {
        return messageRepository.findByChatIdWithLimitAndOffset(chatId, limit, msgCount)
                .stream()
                .sorted(Comparator.comparing(Message::getDateTime))
                .collect(Collectors.toList());
    }
}
