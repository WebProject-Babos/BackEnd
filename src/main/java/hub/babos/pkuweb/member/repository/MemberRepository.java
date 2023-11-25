package hub.babos.pkuweb.member.repository;

import hub.babos.pkuweb.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNickname(String nickname);
    Optional<Member> findByEmailAndPassword(String email, String password);
    boolean existsMemberByEmail(String email);
    boolean existsMemberByNickname(String nickname);
}
