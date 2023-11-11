package hub.babos.pkuweb.message.repository;

import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findMessagesByReceiver(Member receiver);
    List<Message> findMessagesBySender(Member receiver);
}
