package hub.babos.pkuweb.auth.repository;

import hub.babos.pkuweb.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByMemberId(Long memberId);

    void deleteAllByMemberId(Long memberId);
}
