package psk.projects.dating_portal.chat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

@Service
@AllArgsConstructor
public class RegisterBrokerService {

    private final NotificationRepository notificationRepository;
    private final ConcurrentHashMap<Long, SynchronousQueue<NotificationWithMessage>> map = new ConcurrentHashMap<>();


    public void register(Long userId) {
        if (!map.containsKey(userId)) {
            map.put(userId, new SynchronousQueue<>());
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
            notification.setState(NotificationState.NEW);
            notification = notificationRepository.save(notification);

            if (map.containsKey(notification.getTriggerUserId())) {
                map.get(notification.getTriggerUserId()).put(NotificationWithMessage.builder().message(message).notification(notification).build());
            }
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }
}
