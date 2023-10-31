package hub.babos.pkuweb.member.repository;

import hub.babos.pkuweb.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
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
        List<Member> members = memberRepository.findByNickname("nickname2");
        Assertions.assertThat(members.size()).isEqualTo(1);
    }
}