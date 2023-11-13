package hub.babos.pkuweb.post.repository;

import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(Member author);
}
