package hub.babos.pkuweb.like.domain;

import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.post.domain.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Post post;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Member member;


    @Builder
    public PostLike(Long id, Post post, Member member) {
        this.id = id;
        this.post = post;
        this.member = member;
    }

    public boolean isLiked(Long memberId) {
        return member.hasId(memberId);
    }

    public void delete() {
        this.post = null;
    }
}
