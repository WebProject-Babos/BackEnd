package hub.babos.pkuweb.post.domain;

import hub.babos.pkuweb.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "author", referencedColumnName = "id")
    Member author;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;


    @Builder
    public Post(Long id, String title, String content, Member author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }
}