package psk.projects.dating_portal.chat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

@Service
@AllArgsConstructor
public class RegisterBrokerService {

    private final NotificationRepository notificationRepository;
    private final ConcurrentHashMap<Long, LinkedBlockingQueue<NotificationWithMessage>> map = new ConcurrentHashMap<>();

    /**
     * rejestruje uzytkownika w brokerze wiadomosci - bedzie on otrzymywal notifikacje z chatu
     * Jezeli uzytkownik jest juz zarejestrowany metoda nic nie zrobi
     * @param userId
     */
    public void register(Long userId) {
        if (!map.containsKey(userId)) {
            map.put(userId, new LinkedBlockingQueue<>());
        }
    }

    /**
     * Uzytkownik najpier jest rejestrowany w brokerze. Oczekuje on na jpoawienie sie notyfikacji.
     * Metoda wykorzystywana w chacie i websocketach
     * @param userId
     * @return notyfikacja wraz z wiadomoscia z chatu
     * @throws InterruptedException
     */
    public NotificationWithMessage subscribe(Long userId) throws InterruptedException {
        register(userId);

        NotificationWithMessage notification = map.get(userId).take();
        notification.getNotification().setState(NotificationState.CONSUMED);
        notificationRepository.save(notification.getNotification());
        return notification;
    }

    /**
     * publikowanie wiadomosci - zapisanie danych w bazie oraz propagacja do zarejestrowanych uzytkownikow ktorych dotyczy notyfikacja
     * @param notification
     * @param message
     */
    public void publish(Notification notification, Message message) {
        try {
            String uuid = UUID.randomUUID().toString();
            notification.setState(NotificationState.NEW);
            notification = notificationRepository.save(notification);

            System.out.println(uuid + " - BEFORE PUT");
            if (map.containsKey(notification.getUserId())) {
                map.get(notification.getUserId()).put(NotificationWithMessage.builder().message(message).notification(notification).build());
            }
            System.out.println(uuid + " - AFTER PUT");
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }
}
