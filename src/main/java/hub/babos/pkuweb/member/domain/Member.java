package hub.babos.pkuweb.member.domain;

import hub.babos.pkuweb.auth.service.PasswordManager;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.USER;


    @Builder
    public Member(Long id, String email, String password, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public void encodePassword(PasswordManager passwordManager) {
        this.password = passwordManager.encode(this.password);
    }

    public boolean hasId(Long memberId) {
        return Objects.equals(this.id, memberId);
    }
}
