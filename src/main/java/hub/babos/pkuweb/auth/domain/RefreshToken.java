package hub.babos.pkuweb.auth.domain;

import hub.babos.pkuweb.support.token.exception.InvalidRefreshTokenException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "token")
    private String token;


    @Builder
    public RefreshToken(Long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }

    public void validateSameToken(String token) {
        if (!this.token.equals(token)) {
            throw new InvalidRefreshTokenException();
        }
    }
}
