package hub.babos.pkuweb.member.repository;

import hub.babos.pkuweb.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberRepositoryTest {

    private final MemberRepository memberRepository;


    @Autowired
    MemberRepositoryTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void createMembers() {
        for (int i = 1; i <= 10; i++) {
            Member member = Member.builder()
                    .email(i + "@email.com")
                    .password("password" + i)
                    .nickname("nickname" + i)
                    .build();
            memberRepository.save(member);
        }
    }


    @Test
    @DisplayName("멤버 한 명 추가")
    public void testCreateMember() {
        Member member = Member.builder()
                .id(1L)
                .email("first@first.com")
                .password("first")
                .nickname("first user")
                .build();

        Member savedMember = memberRepository.save(member);

        Assertions.assertThat(member.getId()).isEqualTo(savedMember.getId());
    }

    @Test
    @DisplayName("멤버 닉네임 조회 테스트")
    public void testFindByNickname() {
        createMembers();

        Member member = memberRepository.findByNickname("nickname2");

        Assertions.assertThat(member.getNickname()).isEqualTo("nickname2");
    }

    @Test
    @DisplayName("이메일과 비밀번호로 조회")
    public void findByEmailAndPassword() {
        createMembers();

        Optional<Member> member1 = memberRepository.findByEmailAndPassword("1@email.com", "password1");
        Optional<Member> member2 = memberRepository.findByEmailAndPassword("11@email.com", "password11");

        Assertions.assertThat(member1).isNotEmpty();
        Assertions.assertThat(member2).isEmpty();
    }

    @Test
    @DisplayName("해당 이메일을 가진 멤버가 존재하는지")
    public void existsMemberByEmail() {
        createMembers();

        boolean unique1 = memberRepository.existsMemberByEmail("1@email.com");
        boolean unique2 = memberRepository.existsMemberByEmail("11@email.com");

        Assertions.assertThat(unique1).isEqualTo(true);
        Assertions.assertThat(unique2).isEqualTo(false);
    }

    @Test
    @DisplayName("해당 닉네임을 가진 멤버가 존재하는지")
    public void existsMemberByNickname() {
        createMembers();

        boolean unique1 = memberRepository.existsMemberByNickname("nickname1");
        boolean unique2 = memberRepository.existsMemberByEmail("nickname11");

        Assertions.assertThat(unique1).isEqualTo(true);
        Assertions.assertThat(unique2).isEqualTo(false);
    }
}