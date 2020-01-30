package psk.projects.dating_portal.chat;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    List<Chat> findBySearchInfoLike(String queryStr);
    List<Chat> findBySearchInfo(String queryStr);
}

interface ChatContributorRepository extends CrudRepository<ChatContributor, Long> {
}

interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatId(Long chatId, Pageable pageable);

    @Query(value="SELECT msg.* FROM MESSAGE msg where msg.CHAT_ID = ?1 order by msg.DATE_TIME DESC limit ?2 offset ?3", nativeQuery = true)
    List<Message> findByChatIdWithLimitAndOffset(Long chatId, int limit, int offset);
}