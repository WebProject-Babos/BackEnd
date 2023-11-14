package hub.babos.pkuweb.like.repository;

import hub.babos.pkuweb.like.domain.PostLike;
import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByPostAndMember(Post post, Member member);

    boolean existsByPostAndMember(Post post, Member member);
}
