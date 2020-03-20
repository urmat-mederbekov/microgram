package kz.attractorschool.microgram.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Document(collection = "comments")
@Data
public class Comment {

    @Id
    private String id;
    @Indexed
    private String path;
    @DBRef
    private Post post;
    private LocalDateTime dateTime;
    private String text;
    @Indexed
    private User commenter;

    public Comment(Post post, User commenter, String text) {
        this.id = UUID.randomUUID().toString();
        this.post = post;
        this.dateTime = LocalDateTime.now();
        this.commenter = commenter;
        this.text = text;
    }

    public Comment(String path, Post post, User commenter, String text) {
        new Comment(post, commenter, text);
        this.path = path;
    }
}
