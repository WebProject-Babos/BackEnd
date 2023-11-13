package hub.babos.pkuweb.comment.domain;

import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.post.domain.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Post post;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Comment(Long id, Member member, Post post, String content, LocalDateTime createdAt) {
        this.id = id;
        this.member = member;
        this.post = post;
        this.content = content;
        this.createdAt = createdAt;
    }
}
