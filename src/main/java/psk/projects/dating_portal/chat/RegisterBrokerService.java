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

    public void register(Long userId) {
        if (!map.containsKey(userId)) {
            map.put(userId, new LinkedBlockingQueue<>());
        }
    }

    public NotificationWithMessage subscribe(Long userId) throws InterruptedException {
        register(userId);

        NotificationWithMessage notification = map.get(userId).take();
        notification.getNotification().setState(NotificationState.CONSUMED);
        notificationRepository.save(notification.getNotification());
        return notification;
    }

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
