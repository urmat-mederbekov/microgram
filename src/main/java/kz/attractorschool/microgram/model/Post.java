package kz.attractorschool.microgram.model;

import kz.attractorschool.microgram.utils.Generator;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Document(collection = "posts")
@Data
public class Post {
    static Generator gen = new Generator();

    @Id
    private String id;
//    @DBRef
//    private PostImage image;
    private String image;
    private String description;
    private LocalDateTime dateTime;
    @Indexed
    private int numOfLikes;
    private int numOfComments;
    @DBRef
    private User user;

    public Post(String description, User user, String image) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.dateTime = LocalDateTime.now();
        this.user = user;
        this.image = image;
    }
}