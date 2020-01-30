package psk.projects.dating_portal.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationWithMessage {
    Message message;
    Notification notification;
}
