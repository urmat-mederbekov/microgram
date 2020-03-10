package kz.attractorschool.microgram.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Likes")
@Data
public class Liked {

    @Id
    private String id;
    private String user;
    private String post;
    private LocalDateTime dateTime;

    public Liked(String id, String user, String post, LocalDateTime dateTime) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.dateTime = dateTime;
    }
}
